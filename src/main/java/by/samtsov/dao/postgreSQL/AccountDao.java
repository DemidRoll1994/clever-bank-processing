package by.samtsov.dao.postgreSQL;

import by.samtsov.dao.iAccountDao;
import by.samtsov.model.Account;
import by.samtsov.model.Bank;
import by.samtsov.model.Client;
import by.samtsov.model.Currency;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDao implements iAccountDao {
    @Override
    public List<Account> findByClientId(int clientId) {
        String SQL_SELECT = "Select * from clevertec_bank.public.accounts " +
                "where client_id=" + clientId + " order by id";
        Connection conn = PostgreSQLConnUtils.getPostgreConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                var accountBuilder = Account.builder();

                var id = resultSet.getInt("id");
                accountBuilder.id(id);

                var ibanCode = resultSet.getString("iban_code");
                if (ibanCode != null && !ibanCode.isEmpty()) {
                    accountBuilder.ibanCode(ibanCode);
                }

                var selectedClientId = resultSet.getInt("client_id");
                accountBuilder.client(Client
                        .builder()
                        .id(selectedClientId)
                        .build());

                var bankId = resultSet.getInt("bank_id");
                accountBuilder.bank(Bank
                        .builder()
                        .id(bankId)
                        .build());

                var amount = resultSet.getBigDecimal("amount");
                accountBuilder.amount(amount);

                var opening_date = resultSet.getDate("opening_date");
                accountBuilder.openengDate(opening_date);


                int currency_id = resultSet.getInt("currency_id") - 1;
                Currency currency = null;
                if (currency_id < Currency.values().length) {
                    currency = Currency.getByIndex(currency_id);
                }
                accountBuilder.currency(currency);
                accounts.add(accountBuilder.build());
            }
            //loging client select query sout(client)

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public int increaseAmountAtAccount(int accountId, BigDecimal amount) {
        String SQL_UPDATE = String.format("UPDATE clevertec_bank.public" +
                        ".accounts SET amount=amount+(%s) where id=%d " +
                        "and amount >= %s",
                amount, accountId, amount);
        Connection conn = PostgreSQLConnUtils.getPostgreConnection();
        int result = 0;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_UPDATE);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Account> findByIBAN(String iban) {
        String SQL_SELECT = "Select * from clevertec_bank.public.accounts " +
                "order by id";
        Connection conn = PostgreSQLConnUtils.getPostgreConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            var accountBuilder = Account.builder();

            var id = resultSet.getInt("id");
            accountBuilder.id(id);

            var ibanCode = resultSet.getString("iban_code");
            if (ibanCode != null && !ibanCode.isEmpty()) {
                accountBuilder.ibanCode(ibanCode);
            }

            var selectedClientId = resultSet.getInt("client_id");
            accountBuilder.client(Client
                    .builder()
                    .id(selectedClientId)
                    .build());

            var bankId = resultSet.getInt("bank_id");
            accountBuilder.bank(Bank
                    .builder()
                    .id(bankId)
                    .build());

            var amount = resultSet.getBigDecimal("amount");
            accountBuilder.amount(amount);

            var opening_date = resultSet.getDate("opening_date");
            accountBuilder.openengDate(opening_date);


            int currency_id = resultSet.getInt("currency_id") - 1;
            Currency currency = null;
            if (currency_id < Currency.values().length) {
                currency = Currency.getByIndex(currency_id);
            }
            accountBuilder.currency(currency);
            return Optional.of(accountBuilder.build());

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean transfer(int accountId1, int accountId2, BigDecimal amount) {
        String SQL_UPDATE1 = String.format("""
                        UPDATE clevertec_bank.public.accounts SET amount=amount-%s where id=%d and amount >= %s;""",
                amount, accountId1, amount);
        String SQL_UPDATE2 = String.format(
                "UPDATE clevertec_bank.public.accounts " +
                        "SET amount=amount+%s where id=%d;", amount, accountId2);
        String SQL_Select = String.format("Select amount from " +
                "clevertec_bank.public.accounts where id=%d;", accountId1);
        String SQL_UPDATE3 = "Commit;";
        Connection conn = PostgreSQLConnUtils.getPostgreConnection();
        try {
            PreparedStatement preparedStatement =
                    conn.prepareStatement(SQL_UPDATE1);

            conn.prepareStatement(SQL_UPDATE1).executeUpdate();
                    conn.prepareStatement(SQL_UPDATE2).executeUpdate();
            /*
            if (conn.prepareStatement(SQL_UPDATE1).executeUpdate() < 1 ||
                    conn.prepareStatement(SQL_UPDATE2).executeUpdate() < 1) {
                rollback();
                return false;
            }*/
            ResultSet resultSet =
                    conn.prepareStatement(SQL_Select).executeQuery();
            resultSet.next();
            BigDecimal resultAmount = resultSet.getBigDecimal("amount");
            if (resultAmount.compareTo(new BigDecimal(0)) < 0){
                rollback();
                return false;
            }else {
                conn.prepareStatement(SQL_UPDATE3).executeUpdate();
            }


        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void rollback() {
        String SQL_UPDATE = "Rollback;";
        Connection conn = PostgreSQLConnUtils.getPostgreConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_UPDATE);
            preparedStatement.executeUpdate() ;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
