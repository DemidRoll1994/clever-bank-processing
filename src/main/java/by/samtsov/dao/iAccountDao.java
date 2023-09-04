package by.samtsov.dao;

import by.samtsov.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface iAccountDao {
    List<Account> findByClientId(int clientId);

    int increaseAmountAtAccount(int id, BigDecimal amount);

    Optional<Account> findByIBAN(String iban);

    boolean transfer(int accountId1, int accountId2, BigDecimal amount);
}
