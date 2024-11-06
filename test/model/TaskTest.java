package model;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void shoudEqualsIfSameId() { //экз.TASK должны совпадать если равны ID
        Task task1 = new Task("Task1", "TDescript1", Status.NEW, LocalDateTime.now(), Duration.ZERO, 1);
        Task task2 = new Task("Task2", "TDescript2", Status.NEW, LocalDateTime.now(), Duration.ZERO, 1);

        assertEquals(task1, task2, "Объекты ТАСК не равны");
    }

    @Test
    void successorsShoudBeEqualsIfSameId() { //наследники должны быть равны по ID
        Epic epic1 = new Epic("Epic1", "EDescript1", Status.NEW);
        //epic1.setId(1);
        SubTask subTask1 = new SubTask("subtask1", "StDescript1", Status.NEW, 0);
        //subTask1.setId(1);
        assertEquals(epic1.getId(), subTask1.getId(), "Наследник не равен другому наследнику");
    }

    @Test
    void impossiblePutEpicToEpic() { //Epic нельзя добавить в самого себя
        Epic epic1 = new Epic("Epic1", "EDescript1", Status.NEW);
        epic1.addSubTasks(epic1.getId());
        assertEquals(new ArrayList<>(), epic1.getListSubTasksId(), "нельзя добавить Епик в самого себя");
    }

    @Test
    void impossiblePutThisSubTaskAsEpicToHimSelf() { //СабТаск нельзя сделать своим же эпиком
        SubTask subTask1 = new SubTask("subtask1", "StDescript1", Status.NEW,
                LocalDateTime.now(), Duration.ZERO, 0, 0);
        assertNotEquals(subTask1.getEpicId(), subTask1.getId());
    }


}