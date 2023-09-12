package ru.shefer;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class StudentSurnameStorage {
    private final TreeMap<String, Set<Long>> surnamesTreeMap;

    public StudentSurnameStorage() {
        surnamesTreeMap = new TreeMap<>();
    }

    public void studentCreated(Long id, String surname) {
        Set<Long> existingIds = surnamesTreeMap.getOrDefault(surname, new HashSet<>());
        existingIds.add(id);
        surnamesTreeMap.put(surname, existingIds);
    }

    public void studentDeleted(Long id, String surname) {
        surnamesTreeMap.get(surname).remove(id);
    }

    public void studentUpdated(Long id, String oldSurname, String newSurname) {
        studentDeleted(id, oldSurname);
        studentCreated(id, newSurname);
    }


    /**
     * Данный метод возвращает уникальные идентификаторы студентов,
     * чьи фамилии меньше или равны переданной.
     *
     * @return set
     */
    public Set<Long> getStudentsBySurnamesBetweenGiven(String firstSurname, String secondSurname) {
        return surnamesTreeMap.subMap(firstSurname, true, secondSurname, true)
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public Set<Long> getStudentsBySurname(String surname) {
        return surnamesTreeMap.get(surname);
    }
}
