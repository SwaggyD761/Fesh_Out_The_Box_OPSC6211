package com.example.demo001_opsc

import java.sql.*

class DatabaseQueryHandler {

    private val url = "jdbc:mysql://localhost/fresh_out_the_box"
    private val username = "root"
    private val password = ""
    private val connection: Connection = DriverManager.getConnection(url, username, password)

    fun userVerification(email: String, password: String): Any {
        val stmt = connection.createStatement()

        val selectQuery = "SELECT Password FROM users WHERE Email='$email'"
        val result = stmt.executeQuery(selectQuery)

        if (!result.next()) {
            return "Email does not exist"
        }

        val dbPassword = result.getString("Password")
        if (dbPassword == password) {
            return true
        } else {
            return "Enter correct email or password"
        }
    }

    fun createUser(name: String, surname: String, cellNumber: String, email: String, password: String): Any {
        val stmt = connection.createStatement()

        val selectQuery = "SELECT * FROM users WHERE Email='$email'"
        val result = stmt.executeQuery(selectQuery)

        if (result.next()) {
            return "Email already exists"
        }

        val insertQuery = "INSERT INTO users (Name, Surname, cell_number, Email, Password, achievement_status) " +
                "VALUES ('$name', '$surname', '$cellNumber', '$email', '$password', 'none')"
        stmt.executeUpdate(insertQuery)

        return true
    }

    fun createSneaker(user_id: Int, sneaker_name: String, brand_name: String, price: Double, condition: String, date_of_purchase: Date, date_of_upload: Date): Boolean {
        val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM User_sneakers WHERE User_id = ? AND Sneaker_name = ? AND Brand_name = ? AND Price = ? AND Condition = ? AND Date_of_purchase = ? AND Date_of_upload = ?")
        statement.setInt(1, user_id)
        statement.setString(2, sneaker_name)
        statement.setString(3, brand_name)
        statement.setDouble(4, price)
        statement.setString(5, condition)
        statement.setDate(6, java.sql.Date(date_of_purchase.time))
        statement.setDate(7, java.sql.Date(date_of_upload.time))
        val resultSet: ResultSet = statement.executeQuery()
        return if (resultSet.next()) {
            println("Sneaker already exists")
            false
        } else {
            val insertStatement: PreparedStatement = connection.prepareStatement("INSERT INTO User_sneakers(User_id, Sneaker_name, Brand_name, Price, Condition, Date_of_purchase, Date_of_upload) VALUES (?, ?, ?, ?, ?, ?, ?)")
            insertStatement.setInt(1, user_id)
            insertStatement.setString(2, sneaker_name)
            insertStatement.setString(3, brand_name)
            insertStatement.setDouble(4, price)
            insertStatement.setString(5, condition)
            insertStatement.setDate(6, java.sql.Date(date_of_purchase.time))
            insertStatement.setDate(7, java.sql.Date(date_of_upload.time))
            insertStatement.executeUpdate()
            println("Sneaker created successfully")
            true
        }
    }


    fun findSneaker(user_id: Int, sneaker_id: Int): Sneaker? {
        val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM User_sneakers WHERE User_id = ? AND Sneaker_id = ?")
        statement.setInt(1, user_id)
        statement.setInt(2, sneaker_id)
        val resultSet: ResultSet = statement.executeQuery()
        return if (resultSet.next()) {
            Sneaker(
                resultSet.getInt("Sneaker_id"),
                resultSet.getInt("User_id"),
                resultSet.getString("Sneaker_name"),
                resultSet.getString("Brand_name"),
                resultSet.getDouble("Price"),
                resultSet.getString("Condition"),
                resultSet.getDate("Date_of_purchase"),
                resultSet.getDate("Date_of_upload")
            )
        } else {
            null
        }
    }

    fun displaySneakers(user_id: Int): List<Sneaker> {
        val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM User_sneakers WHERE User_id = ?")
        statement.setInt(1, user_id)
        val resultSet: ResultSet = statement.executeQuery()
        val sneakers: MutableList<Sneaker> = mutableListOf()
        while (resultSet.next()) {
            sneakers.add(
                Sneaker(
                    resultSet.getInt("Sneaker_id"),
                    resultSet.getInt("User_id"),
                    resultSet.getString("Sneaker_name"),
                    resultSet.getString("Brand_name"),
                    resultSet.getDouble("Price"),
                    resultSet.getString("Condition"),
                    resultSet.getDate("Date_of_purchase"),
                    resultSet.getDate("Date_of_upload")
                )
            )
        }
        return sneakers
    }

    fun createCollection(User_id: Int, Collection_name: String, date_of_creation: Date, collected_sneakers: String) {
        val stmt = connection.createStatement()

        // Check if a record with the same data exists
        val result = stmt.executeQuery("SELECT * FROM User_sneaker_Collection WHERE User_id=$User_id AND Collection_name='$Collection_name' AND date_of_creation='$date_of_creation' AND collected_sneakers='$collected_sneakers'")
        if (result.next()) {
            println("Collection exists")
            return
        }

        // Insert a new record into User_sneaker_Collection table
        val sql = "INSERT INTO User_sneaker_Collection(User_id, Collection_name, date_of_creation, collected_sneakers) VALUES ($User_id, '$Collection_name', '$date_of_creation', '$collected_sneakers')"
        stmt.executeUpdate(sql)
        println("New collection created")
    }

    fun findCollection(user_id: Int, collection_id: Int) {
        val stmt = connection.createStatement()

        // Fetch data about a collection
        val result = stmt.executeQuery("SELECT * FROM User_sneaker_Collection WHERE User_id=$user_id AND Collection_id=$collection_id")
        if (result.next()) {
            val Collection_name = result.getString("Collection_name")
            val date_of_creation = result.getDate("date_of_creation")
            val collected_sneakers = result.getString("collected_sneakers")
            println("Collection: $Collection_name, Date of Creation: $date_of_creation, Collected Sneakers: $collected_sneakers")
        } else {
            println("Collection not found")
        }
    }

    fun displayCollections(user_id: Int) {
        val stmt = connection.createStatement()

        // Retrieve all records from User_sneaker_Collection table with a user_id that matches user_id that was passed
        val result = stmt.executeQuery("SELECT * FROM User_sneaker_Collection WHERE User_id=$user_id")
        while (result.next()) {
            val collection_id = result.getInt("Collection_id")
            val Collection_name = result.getString("Collection_name")
            val date_of_creation = result.getDate("date_of_creation")
            val collected_sneakers = result.getString("collected_sneakers")
            println("Collection ID: $collection_id, Collection: $Collection_name, Date of Creation: $date_of_creation, Collected Sneakers: $collected_sneakers")
        }
    }



}

class Sneaker(
    int: Int,
    int1: Int,
    string: String,
    string1: String,
    double: Double,
    string2: String,
    date: Date,
    date2: Date
) {

}
