package by.samtsov.controller.command;

import by.samtsov.view.ConsoleUtil;

import java.util.Optional;

public abstract class Command {

    public Command() {}

    public Optional<Command> execute() {
        askQuestion();
        String answer = getAnswer();
        return doAction(answer);
    }

    protected abstract String getQuestion();

    private void askQuestion() {
        ConsoleUtil.putMessage(getQuestion());
    }
    protected String getAnswer() {
        return ConsoleUtil.getMessage();
    }


    public abstract Optional<Command> doAction(String userAnswer);
}
