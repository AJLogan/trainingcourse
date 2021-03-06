package demo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScrollableUpdatableDemo {

	public static void main(String[] args) {

		// Set up a default JDBC driver and database name.
		String jdbcDriver = "com.mysql.jdbc.Driver";
		String databaseUri = "jdbc:mysql://localhost:8889/javaDemoDB?"
				+ "user=root&password=root";

		// Load JDBC driver.
		try {
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e) {
			System.out.println("Error loading JDBC driver: " + e);
		}

		// Connect to a database.
		Connection cn = null;
		try {
			cn = DriverManager.getConnection(databaseUri);
		} catch (SQLException e) {
			System.out.println("Error connecting to a database: " + e);
		}

		// Perform a scrollable update.
		try {
			Statement st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet rs = st
					.executeQuery("SELECT Name, Region FROM Employees WHERE REGION='London'");

			rs.afterLast();
			while (rs.previous() != false) {
				System.out.println("Employee name: " + rs.getString("Name"));
				rs.updateString("Region", "Bracknell");
				rs.updateRow();
			}
		} catch (SQLException e) {
			System.out.println("Error occurred: " + e);
		}
	}
}
