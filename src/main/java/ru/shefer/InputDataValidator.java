package ru.shefer;

import org.springframework.stereotype.Service;

@Service
public class InputDataValidator {
    public void checkInputData(Action action, String data) throws WrongInputException {
        String[] dataArray;
        if (data == null) {
            dataArray = new String[0];
        } else {
            dataArray = data.split(",");
        }

        switch (action.getCode()) {
            case 1 -> {
                if (dataArray.length != 5) {
                    throw new WrongInputException("Необходимо ввести пять полей через запятую");
                }
                checkAge(dataArray[4]);
            }
            case 2 -> {
                if (dataArray.length != 6) {
                    throw new WrongInputException("Необходимо ввести шесть полей через запятую");
                }
                checkId(dataArray[0]);
                checkAge(dataArray[5]);
            }
            case 3 -> checkId(dataArray[0]);
            case 6 -> {
                if (dataArray.length > 2) {
                    throw new WrongInputException("Неверный ввод данных для поиска");
                }
            }
        }
    }

    public void checkId(String stringId) throws WrongInputException {
        try {
            Integer.parseInt(stringId.trim());
        } catch (NumberFormatException e) {
            throw new WrongInputException("Первым полем необходимо ввести идентификатор студента," +
                    "данные которого необходимо обновить, в числовом формате.");
        }
    }

    public void checkAge(String stringAge) throws WrongInputException {
        try {
            int age = Integer.parseInt(stringAge.trim());
            if (age < 16) {
                throw new WrongInputException("Студент должен быть старше 16 лет");
            } else if (age > 90) {
                throw new WrongInputException("Проверьте корректность введенного возраста (введен возраст больше 90 лет)");
            }
        } catch (NumberFormatException e) {
            throw new WrongInputException("Возраст необходимо ввести в числовом формате");
        }
    }
}
