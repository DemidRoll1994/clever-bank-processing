package by.samtsov.dao.postgreSQL;

import by.samtsov.dao.iBankDao;
import by.samtsov.model.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankDao implements iBankDao {
    @Override
    public Bank getBankById(int bankId) {

        String SQL_SELECT = "Select * from clevertec_bank.public.banks " +
                "where id=" + bankId + " order by id";
        Connection conn = PostgreSQLConnUtils.getPostgreConnection();
        Bank bank = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                var bankBuilder = Bank.builder();
                var id = resultSet.getInt("id");
                bankBuilder.id(id);
                var name = resultSet.getString("name");
                if (name != null && !name.isEmpty()) {
                    bankBuilder.name(name);
                }
                var ibanCode = resultSet.getString("iban_code");
                if (ibanCode != null && !ibanCode.isEmpty()) {
                    bankBuilder.IBANCode(ibanCode);
                }
                bank = bankBuilder.build();
            }
            //loging client select query sout(client)

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bank;
    }
}
