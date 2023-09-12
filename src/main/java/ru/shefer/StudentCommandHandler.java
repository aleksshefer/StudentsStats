package ru.shefer;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StudentCommandHandler {
    private final StudentStorage studentStorage;

    public StudentCommandHandler(StudentStorage studentStorage) {
        this.studentStorage = studentStorage;
    }


    public void processCommand(Command command) {
        System.out.println("Обработка команды."
                + " Действие: " + command.getAction().name()
                + ", данные: " + command.getData());
        Action action = command.getAction();

        switch (action) {
            case CREATE -> processCreateCommand(command);
            case UPDATE -> processUpdateCommand(command);
            case DELETE -> processDeleteCommand(command);
            case STATS_BY_COURSE -> processStatsByCourseCommand(command);
            case STATS_BY_CITY -> processStatsByCityCommand(command);
            case SEARCH -> processSearchCommand(command);
            case ERROR -> System.out.println(command.getData());
            default -> System.out.println("Действие " + action + " не поддерживается");
        }
    }

    private void processStatsByCityCommand(Command command) {
        Map<String, Long> data = studentStorage.getCountByCity();
        studentStorage.printMap(data);
    }

    private void processSearchCommand(Command command) {
        studentStorage.search(command.getData());
    }

    private void processStatsByCourseCommand(Command command) {
        Map<String, Long> data = studentStorage.getCountByCourse();
        studentStorage.printMap(data);
    }

    private void processCreateCommand(Command command) {
        String data = command.getData();
        String[] dataArray = data.split(",");

        Student student = new Student();
        student.setSurname(dataArray[0].trim());
        student.setName(dataArray[1].trim());
        student.setCourse(dataArray[2].trim());
        student.setCity(dataArray[3].trim());
        student.setAge(Integer.valueOf(dataArray[4].trim()));

        studentStorage.createStudent(student);
        studentStorage.printAll();
    }

    private void processUpdateCommand(Command command) {
        String data = command.getData();
        String[] dataArray = data.split(",");

        Long id = Long.valueOf(dataArray[0].trim());
        Student student = new Student();
        student.setSurname(dataArray[1].trim());
        student.setName(dataArray[2].trim());
        student.setCourse(dataArray[3].trim());
        student.setCity(dataArray[4].trim());
        student.setAge(Integer.valueOf(dataArray[5].trim()));

        studentStorage.updateStudent(id, student);
        studentStorage.printAll();
    }

    private void processDeleteCommand(Command command) {
        String data = command.getData();
        Long id = Long.valueOf(data);

        studentStorage.deleteStudent(id);
        studentStorage.printAll();
    }
}
