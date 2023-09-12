package ru.shefer;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CommandReader {
    private final InputDataValidator validator;

    public CommandReader(InputDataValidator validator) {
        this.validator = validator;
    }

    public Command readCommand() {
        Scanner scanner = new Scanner(System.in);
        try {
            String code = scanner.nextLine();
            Integer actionCode = Integer.valueOf(code);
            Action action = Action.fromCode(actionCode);
            if (action.isRequireAdditionalData()) {
                String data = scanner.nextLine();
                validator.checkInputData(action, data);
                return new Command(action, data);
            } else {
                return new Command(action);
            }
        } catch (NumberFormatException e) {
            return new Command(Action.ERROR, "В качестве команды необходимо ввести число! " + e.getMessage());
        } catch (WrongInputException e) {
            return new Command(Action.ERROR, e.getMessage());
        }
    }
}
