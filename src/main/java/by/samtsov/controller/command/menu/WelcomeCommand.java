package by.samtsov.controller.command.menu;

import by.samtsov.controller.command.Command;
import by.samtsov.controller.command.SelectActionCommand;
import by.samtsov.model.Client;
import by.samtsov.service.ClientService;

import java.util.Optional;

public class WelcomeCommand extends Command {
    private final static String QUESTION = "выберите клиента:";
    private final String NO_SUCH_USER_FOUND = "Не найден подходящий " +
            "пользователь";

    public WelcomeCommand() {
        super();
    }

    @Override
    protected String getQuestion() {
        return QUESTION;
    }

    @Override
    public Optional<Command> doAction(String userAnswer) {
        var clientService = new ClientService();
        Client client = null;
        if (userAnswer != null && userAnswer.isEmpty() != true) {
            try {
                Integer clientId = Integer.valueOf(userAnswer);
                client = clientService.getClientById(clientId);
            } catch (NumberFormatException e) {
                Optional.of(new ErrorCommand(this, "Введите номер"));
            }
        }
        if (client == null) {
            return Optional.of(new ErrorCommand(this, NO_SUCH_USER_FOUND));
        }
        return Optional.of(new SelectActionCommand(client));
    }

}
