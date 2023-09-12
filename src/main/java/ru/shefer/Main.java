package ru.shefer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        StudentCommandHandler studentCommandHandler = context.getBean(StudentCommandHandler.class);
        CommandReader reader = context.getBean(CommandReader.class);
        MessageProvider messageConsumer = context.getBean(MessageProvider.class);

        while (true) {
            messageConsumer.printMessage();
            Command command = reader.readCommand();
            if (command.getAction().equals(Action.EXIT)) {
                return;
            } else {
                studentCommandHandler.processCommand(command);
            }
        }
    }
}