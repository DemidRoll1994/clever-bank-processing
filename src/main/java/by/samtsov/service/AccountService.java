package by.samtsov.service;

import by.samtsov.dao.iAccountDao;
import by.samtsov.dao.iBankDao;
import by.samtsov.dao.postgreSQL.AccountDao;
import by.samtsov.dao.postgreSQL.BankDao;
import by.samtsov.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AccountService {
    private static iAccountDao accountDao = new AccountDao();
    private static iBankDao bankDao = new BankDao();

    public List<Account> findByClientId(int clientId) {
        return accountDao.findByClientId(clientId);
    }

    public boolean increaseAmount(Account account, BigDecimal amount) {
        return accountDao.increaseAmountAtAccount(account.getId(), amount) > 0;
    }

    public Optional<Account> findByIBAN(String IBAN) {
        Optional<Account> account = accountDao.findByIBAN(IBAN);
        if (account.isPresent()) {
            account.get().setBank(bankDao.getBankById(account.get().getBank().getId()));
        }
        return account;
    }

    public boolean transfer(Account account1, Account account2, BigDecimal amount) {
        return accountDao.transfer(account1.getId(),
                account2.getId(), amount);

    }
}
