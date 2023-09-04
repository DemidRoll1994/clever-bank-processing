package by.samtsov.controller.command;

import by.samtsov.controller.command.menu.ErrorCommand;
import by.samtsov.model.Account;
import by.samtsov.model.Client;

import java.math.BigDecimal;
import java.util.Optional;

public class EnterAmountTransferCommand extends Command{
    private static final String QUESTION = "Введите сумму:\n";
    private Client client;
    private Account account1;
    private Account account2;

    public EnterAmountTransferCommand(Client client, Account account1, Account account2) {
        this.client = client;
        this.account1 = account1;
        this.account2 = account2;
    }

    @Override
    protected String getQuestion() {
        return QUESTION;
    }

    @Override
    public Optional<Command> doAction(String userAnswer) {
        Optional<Command> answerCommand;
        if (userAnswer == null || userAnswer.isEmpty()) {
            return Optional.of(new NoMatchingCommand(this));
        }
        try {
            BigDecimal amount = new BigDecimal(userAnswer);
            if (amount.doubleValue() < 0
                            || account1.getAmount().compareTo(amount) < 0) {
                answerCommand = Optional.of(new ErrorCommand(this,
                        "Недостаточно средств"));
            } else {
                answerCommand = Optional.of(new ConfirmTransferCommand(
                        client, account1, account2, amount));
            }
        } catch (Exception e) {
            answerCommand = Optional.of(new ErrorCommand(this, "Неправильно введено " +
                    "количество"));
        }
        return answerCommand;
    }
}
