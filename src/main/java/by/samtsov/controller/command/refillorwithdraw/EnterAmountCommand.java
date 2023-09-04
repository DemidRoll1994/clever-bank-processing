package by.samtsov.controller.command.refillorwithdraw;

import by.samtsov.controller.command.Command;
import by.samtsov.controller.command.NoMatchingCommand;
import by.samtsov.controller.command.menu.ErrorCommand;
import by.samtsov.model.Account;
import by.samtsov.model.Client;
import by.samtsov.model.TransactionType;

import java.math.BigDecimal;
import java.util.Optional;

public class EnterAmountCommand extends Command {
    private static final String QUESTION = "Введите сумму:\n";
    private Client client;
    private Account account;
    private TransactionType type;

    public EnterAmountCommand(Client client, Account account, TransactionType type) {
        this.client = client;
        this.account = account;
        this.type = type;
    }


    @Override
    protected String getQuestion() {
        return QUESTION;
    }

    @Override
    public Optional<Command> doAction(String userAnswer) {
        Optional<Command> answerCommand = Optional.empty();
        if (userAnswer == null || userAnswer.isEmpty()) {
            return Optional.of(new NoMatchingCommand(this));
        }
        try {
            BigDecimal amount = new BigDecimal(userAnswer);
            if (type == TransactionType.WITHDRAW &&
                    (amount.doubleValue() < 0
                            || account.getAmount().compareTo(amount) < 0)) {
                answerCommand = Optional.of(new ErrorCommand(this,
                        "Недостаточно средств"));
            } else {
                answerCommand = Optional.of(new ConfirmRefillWithdrawCommand(
                        client, account, amount, type));
            }
        } catch (Exception e) {
            answerCommand = Optional.of(new ErrorCommand(this, "Неправильно введено " +
                    "количество"));
        }
        return answerCommand;
    }
}
