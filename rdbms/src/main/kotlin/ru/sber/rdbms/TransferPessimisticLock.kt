package ru.sber.rdbms

import java.lang.Long.max
import java.lang.Long.min
import java.sql.DriverManager

class TransferPessimisticLock {
    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        val connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/db",
            "postgres",
            "postgres"
        )

        val minId = min(accountId1, accountId2)
        val maxId = max(accountId1, accountId2)

        connection.use { conn ->
            val autoCommit = conn.autoCommit
            try {
                conn.autoCommit = false
                val prepareStatement1 = conn.prepareStatement("select * from account1 where id = $minId for update")
                prepareStatement1.use { statement ->
                    statement.executeQuery()
                }
                val prepareStatement2 =
                    conn.prepareStatement("select * from account1 where id = $maxId for update")
                prepareStatement2.use { statement ->
                    statement.executeQuery()
                }

                val prepareStatement3 =
                    conn.prepareStatement("update account1 set amount = amount - $amount where id = $accountId1")
                prepareStatement3.use { statement ->
                    statement.executeUpdate()
                }
                val prepareStatement4 =
                    conn.prepareStatement("update account1 set amount = amount + $amount where id = $accountId2")
                prepareStatement4.use { statement ->
                    statement.executeUpdate()
                }
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
