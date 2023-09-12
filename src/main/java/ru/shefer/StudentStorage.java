package ru.shefer;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentStorage {
    private final Map<Long, Student> studentStorageMap;
    private Long currentId;
    private final StudentSurnameStorage studentSurnameStorage;

    public StudentStorage(StudentSurnameStorage studentSurnameStorage) {
        this.studentSurnameStorage = studentSurnameStorage;
        this.studentStorageMap = new HashMap<>();
        this.currentId = 0L;
    }

    /**
     * Создание данных о студенте
     *
     * @param student данные студента
     * @return сгенерированный идентификатор студента
     */
    public Long createStudent(Student student) {
        Long id = getNextId();
        studentStorageMap.put(id, student);
        studentSurnameStorage.studentCreated(id, student.getSurname());
        return id;
    }

    /**
     * Обновление данных о студенте
     *
     * @param id      идентификатор студента
     * @param student данные студента
     * @return true если данные обновлены, false если студент не был найден
     */
    public boolean updateStudent(Long id, Student student) {
        if (studentStorageMap.containsKey(id)) {
            studentSurnameStorage.studentUpdated(id,
                    studentStorageMap.get(id).getSurname(),
                    student.getSurname());
            studentStorageMap.put(id, student);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Удаляет данные о студенте
     *
     * @param id идентификатор студента
     * @return true если студент был удален, false если студент не был найден
     */
    public boolean deleteStudent(Long id) {
        Student removed = studentStorageMap.remove(id);
        if (removed != null) {
            studentSurnameStorage.studentDeleted(id, removed.getSurname());
            return true;
        }
        return false;
    }

    public void search(String data) {
        String[] surnames = data.split(",");
        if (surnames.length == 1 && surnames[0].isEmpty()) {
            printAll();
        } else if (surnames.length == 1) {
            Set<Long> students = studentSurnameStorage.getStudentsBySurname(surnames[0]);
            if (students != null && !students.isEmpty()) {
                for (Long studentId : students) {
                    System.out.println(studentStorageMap.get(studentId));
                }
            } else {
                System.out.println("Студентов с такой фамилией не найдено");
            }
        } else {
            Set<Long> students = studentSurnameStorage.getStudentsBySurnamesBetweenGiven(surnames[0], surnames[1]);
            if (students != null && !students.isEmpty()) {
                for (Long studentId : students) {
                    System.out.println(studentStorageMap.get(studentId));
                }
            } else {
                System.out.println("Студентов с такими фамилиями не найдено");
            }
        }
    }

    private Long getNextId() {
        currentId++;
        return currentId;
    }

    public void printAll() {
        System.out.println(studentStorageMap);
    }

    public void printMap(Map<String, Long> map) {
        map.forEach((key, value) -> System.out.println(key + " - " + value));
    }

    public Map<String, Long> getCountByCourse() {
        return studentStorageMap.values().stream()
                .collect(Collectors.toMap(Student::getCourse, student -> 1L, Long::sum));
    }

    public Map<String, Long> getCountByCity() {
        return studentStorageMap.values().stream()
                .collect(Collectors.toMap(Student::getCity, student -> 1L, Long::sum));
    }
}
