package by.samtsov.controller.command.menu;

import by.samtsov.controller.command.Command;

import java.util.Optional;

public class EndCommand extends Command {

    private static final String QUESTION = "Спасибо что выбрали CleverBank!";
    @Override
    protected String getQuestion() {
        return QUESTION;
    }

    @Override
    public Optional<Command> doAction(String userAnswer) {
        return Optional.empty();
    }
}
