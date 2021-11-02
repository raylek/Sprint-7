package ru.sber.rdbms

import java.sql.DriverManager

class TransferConstraint {
    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        val preparedStatementMinus = "UPDATE account1 SET amount = amount - ${amount} WHERE id = ${accountId1}"
        val preparedStatementPlus = "UPDATE account1 SET amount = amount + ${amount} WHERE id = ${accountId2}"

        val connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/db",
            "postgres",
            "postgres"
        )
        connection.use { conn ->
            val preparedStatement1 = conn.prepareStatement(
                "update account1 SET amount = amount - $amount where id = $accountId1"
            )
            val preparedStatement2 = conn.prepareStatement(
                "UPDATE account1 SET amount = amount + $amount WHERE id = $accountId2"
            )

            try {
                preparedStatement1.use {
                    it.execute()
                }
                preparedStatement2.use {
                    it.execute()
                }
            } catch (e: Exception) {
                print(e)
            }
        }
    }
}
