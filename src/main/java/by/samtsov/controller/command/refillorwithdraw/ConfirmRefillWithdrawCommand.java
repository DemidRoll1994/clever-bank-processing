package by.samtsov.controller.command.refillorwithdraw;

import by.samtsov.controller.command.Command;
import by.samtsov.controller.command.NoMatchingCommand;
import by.samtsov.controller.command.menu.ErrorCommand;
import by.samtsov.controller.command.menu.SuccessCommand;
import by.samtsov.controller.command.menu.WelcomeCommand;
import by.samtsov.model.Account;
import by.samtsov.model.Client;
import by.samtsov.model.TransactionType;
import by.samtsov.service.AccountService;

import java.math.BigDecimal;
import java.util.Optional;

public class ConfirmRefillWithdrawCommand extends Command {
    private Client client;
    private Account account;
    private BigDecimal amount;
    private TransactionType type;
    private static final String QUESTION = "Подтвердите что данные верны(y/n)" +
            ":\n";

    public ConfirmRefillWithdrawCommand(Client client, Account account,
                                        BigDecimal amount,
                                        TransactionType transactionType) {
        this.client = client;
        this.account = account;
        this.amount = amount;
        this.type = transactionType;
    }

    @Override
    protected String getQuestion() {
        StringBuffer sBuffer = new StringBuffer(QUESTION);
        sBuffer.append(String.format("""
                        Клиент - %s %s %s
                        Счет - %s | остаток %s %s 
                        Операция - %s %s %s""",
                client.getSurname(), client.getName(), client.getPatronymic(),
                account.getIbanCode(), account.getAmount().toString(),
                account.getCurrency(),
                type.getDescription(), amount.toString(), account.getCurrency()));
        return sBuffer.toString();
    }

    @Override
    public Optional<Command> doAction(String userAnswer) {
        Optional<Command> answerCommand = Optional.empty();
        if (userAnswer == null || userAnswer.isEmpty()) {
            return Optional.of(new NoMatchingCommand(this));
        }
        switch (userAnswer) {
            case "y" -> {
                if (type == TransactionType.WITHDRAW) amount = amount.negate();
                if (new AccountService().increaseAmount(account, amount)) {
                    answerCommand = Optional.of(new SuccessCommand());
                } else {
                    answerCommand = Optional.of(new ErrorCommand(new WelcomeCommand(),
                            "Ошибка при выполнении операции."));
                }
            }
            case "n" -> answerCommand = Optional.of(new WelcomeCommand());
            default -> answerCommand = Optional.of(new NoMatchingCommand(this));
        }
        return answerCommand;
    }
}
