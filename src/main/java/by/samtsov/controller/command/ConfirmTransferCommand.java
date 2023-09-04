package by.samtsov.controller.command;

import by.samtsov.controller.command.menu.ErrorCommand;
import by.samtsov.controller.command.menu.SuccessCommand;
import by.samtsov.controller.command.menu.WelcomeCommand;
import by.samtsov.model.Account;
import by.samtsov.model.Client;
import by.samtsov.service.AccountService;

import java.math.BigDecimal;
import java.util.Optional;

public class ConfirmTransferCommand extends Command {
    private Client client;
    private Account account1;
    private Account account2;
    private BigDecimal amount;
    private static final String QUESTION = "Подтвердите что данные верны(y/n)" +
            ":\n";

    public ConfirmTransferCommand(Client client, Account account1, Account account2, BigDecimal amount) {
        this.client = client;
        this.account1 = account1;
        this.account2 = account2;
        this.amount = amount;
    }

    @Override
    protected String getQuestion() {

        StringBuffer sBuffer = new StringBuffer(QUESTION);
        sBuffer.append(String.format("""
                        Клиент-отправитель - %s %s %s
                        Счет-отправитель - %s | остаток %s %s 
                        Счет-получатель - %s  
                        Перевод - %s %s""",
                client.getSurname(), client.getName(), client.getPatronymic(),
                account1.getIbanCode(), account1.getAmount().toString(),
                account1.getCurrency(),
                account2.getIbanCode(),
                amount.toString(), account1.getCurrency()));
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
                if (new AccountService().transfer(account1,account2, amount)) {
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
