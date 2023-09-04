package by.samtsov.controller;

import by.samtsov.controller.command.Command;
import by.samtsov.controller.command.NoMatchingCommand;
import by.samtsov.controller.command.menu.EndCommand;
import by.samtsov.controller.command.menu.WelcomeCommand;

import java.util.Optional;

public class Menu implements Runnable {
    @Override
    public void run() {
        Optional<Command> answerCommand = new WelcomeCommand().execute();
        boolean needToContinue = true;
        while (needToContinue) {
            answerCommand = answerCommand.get().execute();
            if (answerCommand.isEmpty()) {
                answerCommand = new NoMatchingCommand(new WelcomeCommand()).execute();
            }
            if (answerCommand.get() instanceof EndCommand)
                needToContinue = false;
        }

    }
}
