package by.samtsov.controller.command;

import by.samtsov.controller.command.refillorwithdraw.SelectAccountForRefillCommand;
import by.samtsov.controller.command.refillorwithdraw.SelectAccountForWithdrawCommand;
import by.samtsov.model.Client;

import java.util.Optional;

public class SelectActionCommand extends Command {
    private static final String QUESTION = """
            выполнить перевод -1
            Пополнить счет-2
            Снять со счета-3""";
    private Client client;

    public SelectActionCommand(Client client) {
        this.client = client;
    }

    @Override
    protected String getQuestion() {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(String.format("Выбран %s %s %s, id%d \n",
                client.getSurname(), client.getName(), client.getPatronymic(),
                client.getId()));
        sBuffer.append(QUESTION);
        return sBuffer.toString();
    }

    @Override
    public Optional<Command> doAction(String userAnswer) {
        Optional<Command> answerCommand = Optional.empty();
        if (userAnswer == null || userAnswer.isEmpty()) {
            return Optional.of(new NoMatchingCommand(this));
        }
        return switch (userAnswer) {
            //case null -> Optional.of(new NoMatchingCommand());
            case "1" ->
                    Optional.of(new SelectFirstAccountForMoneyTransferCommand(client));
            case "2" -> Optional.of(new SelectAccountForRefillCommand(client));
            case "3" ->
                    Optional.of(new SelectAccountForWithdrawCommand(client));
            default -> Optional.of(new NoMatchingCommand(this));
        };

    }

}
