package by.samtsov.controller.command.menu;

import by.samtsov.controller.command.Command;

import java.util.Optional;

public class ErrorCommand extends Command {
    private Command returnToCommand;
    private String errorMessage;

    public ErrorCommand(Command returnToCommand, String errorMessage) {
        this.returnToCommand = returnToCommand;
        this.errorMessage = errorMessage;
    }

    @Override
    protected String getQuestion() {
        return errorMessage+"\n Нажмите Ввод чтобы продолжить";
    }

    @Override
    public Optional<Command> doAction(String userAnswer) {
        return Optional.of(returnToCommand);
    }
}
