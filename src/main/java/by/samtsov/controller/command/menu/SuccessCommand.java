package by.samtsov.controller.command.menu;

import by.samtsov.controller.command.Command;
import by.samtsov.controller.command.NoMatchingCommand;

import java.util.Optional;

public class SuccessCommand extends Command {

    private static final String QUESTION = """
            Операция выполнена успешно. 
            Желаете продолжить(y) или завершить работу(n)?
            """;

    @Override
    protected String getQuestion() {
        return QUESTION;
    }

    @Override
    public Optional<Command> doAction(String userAnswer) {
        if (userAnswer == null || userAnswer.isEmpty()) {
            return Optional.of(new NoMatchingCommand(this));
        }
        return switch (userAnswer) {
            case "y" -> Optional.of(new WelcomeCommand());
            case "n" -> Optional.of(new EndCommand());
            default -> Optional.of(new NoMatchingCommand(this));
        };
    }
}
