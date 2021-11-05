package ru.sber.rdbms

import java.sql.DriverManager

class TransferOptimisticLock {
    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        val connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/db",
            "postgres",
            "postgres"
        )
        var currentVersion: Int
        connection.use { conn ->
            val autoCommit = conn.autoCommit
            try {
                conn.autoCommit = false

                val preparedStatement1 = conn.prepareStatement(
                    "select * from account1 where id = $accountId1"
                )

                preparedStatement1.executeQuery().use {
                    it.next()
                    if (it.getInt("amount") - amount < 0)
                        throw Exception()
                    currentVersion = it.getInt("version")
                }

                val preparedStatement2 =
                    conn.prepareStatement(
                        "update account1 set amount = amount - $amount, version = version + 1 " +
                                "where id = $accountId1 and version = $currentVersion"
                    )

                var result = preparedStatement2.executeUpdate()
                if (result == 0)
                    throw Exception()

                val prepareStatement3 =
                    conn.prepareStatement("select * from account1 where id = $accountId2")
                prepareStatement3.executeQuery().use { statement ->
                    statement.next()
                    currentVersion = statement.getInt("version")
                }
                val prepareStatement4 =
                    conn.prepareStatement(
                        "update account1 set amount = amount + $amount, version = version + 1 " +
                                "where id = $accountId2 and version = $currentVersion"
                    )
                result = prepareStatement4.executeUpdate()
                if (result == 0)
                    throw Exception()
                conn.commit()
            } catch (exception: Exception) {
                println(exception.message)
                conn.rollback()
            } finally {
                conn.autoCommit = autoCommit
            }
        }
    }
}
