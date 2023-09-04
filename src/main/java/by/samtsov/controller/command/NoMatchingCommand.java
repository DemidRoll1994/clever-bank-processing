package by.samtsov.controller.command;

import java.util.Optional;

public class NoMatchingCommand extends Command{

    private static final String QUESTION = "Команда не распознана";
    private Command previousCommand;
    public NoMatchingCommand(Command previousCommand) {
        this.previousCommand = previousCommand;
    }

    @Override
    protected String getQuestion() {
        return QUESTION;
    }
    protected String getAnswer(){
        return null;
    }

    @Override
    public Optional<Command> doAction(String userAnswer) {
        return Optional.of(previousCommand);
    }
}
