package into.dao;

import into.models.LiteraryFormat;
import into.util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LiteraryFormatDaoImpl implements LiteraryFormatDao {
    @Override
    public List<LiteraryFormat> getAll() {
        List<LiteraryFormat> allLiteraryFormat = new ArrayList<>();
//        Connection connection = ConnectionUtil.getConnection();
//        Statement getAllFormatStatement = connection.createStatement(); // Объект Statement будет представлять запрос к БД / The Statement object will represent a query to the database
        // объект будет предоставлять запросы (инструкции, команды) которые будут понятны для драйвера / object will provide requests (instructions, commands) that will be understandable to the driver
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllFormatStatement = connection.createStatement()) {
            ResultSet resultSet = getAllFormatStatement // ResultSet определенный набор информации который мы получаем в результате SELECT запроса / ResultSet a specific set of information that we get as a result of a SELECT query
                    .executeQuery("SELECT * FROM literary_formats WHERE is_deleted = false;"); // теперь мы можем получить объекты наших таблиц / then we can get the objects of our tables
            while (resultSet.next()) {
                String format = resultSet.getString("format");
                Long id = resultSet.getObject("id", Long.class);
                LiteraryFormat literaryFormat = new LiteraryFormat();
                literaryFormat.setId(id);
                literaryFormat.setFormat(format);
                allLiteraryFormat.add(literaryFormat);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't get all formats from DB", e);
        }
        return allLiteraryFormat;
    }

    @Override
    public LiteraryFormat create(LiteraryFormat format) {
        String insertFormatRequest = "INSERT INTO literary_formats (format) VALUES (?);"; // SQL Запрос / SQL Query
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createFormatStatement =   // формируем Statement (PreparedStatement) / forming a Statement (PreparedStatement)
                     connection.prepareStatement(insertFormatRequest, Statement.RETURN_GENERATED_KEYS)) { // мы передаем SQL запрос и указываем что в результате хотим получить сгенерированный ключ
            // / we send an SQL query and specify that as a result we want to get the generated key
            createFormatStatement.setString(1, format.getFormat()); // подставляем вместо "знака вопрос" значение которое нам нужно / substitute the value we want instead of the "question" sign
            createFormatStatement.executeUpdate(); // вызываем метод  executeUpdate() и уже без SQL Запроса, так как мы уже передали ранее / call the executeUpdate() method and already without the SQL Query, since we have already passed the
            ResultSet generatedKeys = createFormatStatement.getGeneratedKeys(); // через ключ получаем ResultSet с которого получим идентификатор с генерированной БД / Using the key we get a ResultSet from which we get an identifier with the generated database
            if (generatedKeys.next()) {  // проходим по ResultSet / Pass by ResultSet
                Long id = generatedKeys.getObject(1, Long.class); // получаем id / get id
                format.setId(id); // добавляем в объект / add to the object
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert format to DB ", e);
        }
        return format; // возвращаем объект с id / return the object with the id
    }

    @Override
    public boolean delete(Long id) {
        String deleteFormatRequest = "UPDATE literary_formats SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteFormatStatement =
                     connection.prepareStatement(deleteFormatRequest, Statement.RETURN_GENERATED_KEYS)) {
            deleteFormatStatement.setObject(1, id);
            return deleteFormatStatement.executeUpdate() >= 1; // возвращает true, если было сделано одна или больше операций. / returns true if one or more operations were done.
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete format from DB", e);
        }

    }
}
