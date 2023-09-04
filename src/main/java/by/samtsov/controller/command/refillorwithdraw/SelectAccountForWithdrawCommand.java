package by.samtsov.controller.command.refillorwithdraw;

import by.samtsov.controller.command.Command;
import by.samtsov.controller.command.NoMatchingCommand;
import by.samtsov.controller.command.menu.ErrorCommand;
import by.samtsov.model.Account;
import by.samtsov.model.Client;
import by.samtsov.model.TransactionType;
import by.samtsov.service.AccountService;

import java.util.List;
import java.util.Optional;

public class SelectAccountForWithdrawCommand extends Command {

    private static final String QUESTION = "Выберите счет для снятия " +
            "денежных средств:\n";
    private Client client;
    private List<Account> accountList;

    public SelectAccountForWithdrawCommand(Client client) {
        this.client = client;
    }

    @Override
    protected String getQuestion() {
        StringBuffer sBuffer = new StringBuffer(QUESTION);
        fillAccountList();
        for (int i = 0; i < accountList.size(); i++) {
            sBuffer.append(String.format("%d \t|IBAN %s \t| %s %s \n",
                    i,
                    accountList.get(i).getIbanCode(),
                    accountList.get(i).getAmount().doubleValue(),
                    accountList.get(i).getCurrency().getCode()));
        }
        if (accountList.isEmpty())
            sBuffer.append("У клиента не найдено ни одного счёта");

        return sBuffer.toString();
    }

    @Override
    public Optional<Command> doAction(String userAnswer) {
        Optional<Command> answerCommand = Optional.empty();
        if (userAnswer == null || userAnswer.isEmpty()) {
            return Optional.of(new NoMatchingCommand(this));
        }
        try {
            int accountIndexInList = Integer.parseInt(userAnswer);
            if (accountIndexInList < accountList.size()) {
                answerCommand = Optional.of(new EnterAmountCommand(client,
                        accountList.get(accountIndexInList),
                        TransactionType.WITHDRAW));
            } else {
                answerCommand = Optional.of(new ErrorCommand(this, "Неправильно введено " +
                        "количество"));
            }
        } catch (Exception e) {
            answerCommand = Optional.of(new ErrorCommand(this, "Неправильно введено " +
                    "количество"));
        }


        return answerCommand;
    }

    private void fillAccountList() {
        AccountService accountService = new AccountService();
        this.accountList = accountService.findByClientId(client.getId());
    }
}
