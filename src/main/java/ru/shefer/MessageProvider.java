package ru.shefer;

import org.springframework.stereotype.Component;

@Component
public class MessageProvider {
    public void printMessage() {
        System.out.println("---------------------------------");
        System.out.println("| 0. Выход                      |");
        System.out.println("| 1. Создание данных            |");
        System.out.println("| 2. Обновление данных          |");
        System.out.println("| 3. Удаление данных            |");
        System.out.println("| 4. Вывод статистики по курсам |");
        System.out.println("| 5. Вывод статистики по городам|");
        System.out.println("| 6. Поиск по фамилии           |");
        System.out.println("---------------------------------");
    }


}
