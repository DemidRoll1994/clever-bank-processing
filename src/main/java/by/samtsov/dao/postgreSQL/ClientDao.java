package by.samtsov.dao.postgreSQL;

import by.samtsov.dao.iClientDao;
import by.samtsov.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDao implements iClientDao {

    @Override
    public Client findById(int clientId) {
        String SQL_SELECT = "Select * from clevertec_bank.public.clients " +
                "where id=" + clientId+ " order by id";
        Connection conn = PostgreSQLConnUtils.getPostgreConnection();
        Client client = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                var cLientBuilder = Client.builder();
                var id = resultSet.getInt("id");
                cLientBuilder.id(id);
                var surname = resultSet.getString("surname");
                if (surname != null && !surname.isEmpty()) {
                    cLientBuilder.surname(surname);
                }
                var name = resultSet.getString("firstname");
                if (name != null && !name.isEmpty()) {
                    cLientBuilder.name(name);
                }
                var patronymic = resultSet.getString("patronymic");
                if (patronymic != null && !patronymic.isEmpty()) {
                    cLientBuilder.patronymic(patronymic);
                }
                client = cLientBuilder.build();
            }
            //loging client select query sout(client)

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public List<Client> GetAll() {
        List<Client> result = new ArrayList<>();
        String SQL_SELECT = "Select * from clevertec_bank.public.clients order by id";
        Connection conn = PostgreSQLConnUtils.getPostgreConnection();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                var cLientBuilder = Client.builder();
                var id = resultSet.getInt("id");
                cLientBuilder.id(id);
                var surname = resultSet.getString("surname");
                if (surname != null && !surname.isEmpty()) {
                    cLientBuilder.surname(surname);
                }
                var name = resultSet.getString("firstname");
                if (name != null && !name.isEmpty()) {
                    cLientBuilder.name(name);
                }
                var patronymic = resultSet.getString("patronymic");
                if (patronymic != null && !patronymic.isEmpty()) {
                    cLientBuilder.patronymic(patronymic);
                }
                result.add(cLientBuilder.build());
            }
            //loging client select query sout(client)

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
