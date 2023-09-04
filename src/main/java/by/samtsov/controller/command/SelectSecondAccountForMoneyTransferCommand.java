package by.samtsov.controller.command;

import by.samtsov.controller.command.menu.ErrorCommand;
import by.samtsov.model.Account;
import by.samtsov.model.Client;
import by.samtsov.service.AccountService;

import java.util.Optional;

public class SelectSecondAccountForMoneyTransferCommand extends Command {
    private static final String QUESTION = "Введите IBAN-номер " +
            "счета-получателя:\n";
    private Client client;
    private Account account1;

    public SelectSecondAccountForMoneyTransferCommand(Client client, Account account1) {
        this.client = client;
        this.account1 = account1;
    }

    @Override
    protected String getQuestion() {
        return QUESTION;
    }

    @Override
    public Optional<Command> doAction(String userAnswer) {
        if (userAnswer == null || userAnswer.isEmpty()) {
            return Optional.of(new NoMatchingCommand(this));
        }
        Optional<Account> account = new AccountService().findByIBAN(userAnswer);
        if (account.isEmpty()) {
            return Optional.of(new ErrorCommand(this, "Счёт-получатель не " +
                    "найден"));
        }
        return Optional.of(new EnterAmountTransferCommand(client, account1,
                account.get()));
    }
}
