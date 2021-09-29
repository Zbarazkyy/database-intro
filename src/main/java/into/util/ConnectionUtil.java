package into.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // запрашиваем у библиотеке Driver для подключения, библиотеки перед этим добавили в pom.xml
                                                     // / request from the Driver library to connect, the libraries before that were added to pom.xml
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {


        try {

            Properties dbProperties = new Properties(); // создаем Properties с user name и password чтоб потом добавить путь и Properties в DriverManager
                                                       // / Create Properties with user name and password to add the path and Properties to the DriverManager
            dbProperties.put("user", "root");
            dbProperties.put("password", "passroot");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", dbProperties); // в DriverManager (служебный класс JDBC) вызываем метод .getConnection и реоедаем путь к нашей базе ("jdbc:xxxx:datasource") и Properties
                                                  // / In DriverManager (JDBC service class) we call the .getConnection method and redirect the path to our database ("jdbc:xxxx:datasource") and Properties
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection", throwables);
        }
    }
}
