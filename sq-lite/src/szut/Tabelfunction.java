package szut;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Tabelfunction {
	
	public void insertvalues(){
		
		
	}
	
	
	public void testvalues(Gui gui){
		String[][] ungerade = { { "1", "9", "4" }, { "0", "2" },
				{ "0", "1", "2", "3", "4" } };
		gui.update(ungerade);
		
	}
	

	public void testtabel() throws ClassNotFoundException {

		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");


		Connection connection = null;
		try {
			// create a database connection das
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			statement.executeUpdate("drop table if exists person");
			statement.executeUpdate("create table person (id integer, name string)");
			statement.executeUpdate("insert into person values(1, 'leo')");
			statement.executeUpdate("insert into person values(2, 'yui')");
			statement.executeUpdate("insert into person values(3, 'rrr')");
			statement.executeUpdate("insert into person values(4, 'tzu')");
			ResultSet rs = statement.executeQuery("select * from person");


			while (rs.next()) {
				// read the result set

				System.out.println("name = " + rs.getString("name"));
				System.out.println("id = " + rs.getInt("id"));
			}
			
		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
	}
}
