package service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultRerurnInMemoryTaskManager() {
        //класс возвращает экземпляр менеджера
        TaskManager taskManager=Managers.getDefault();
        assertTrue(taskManager instanceof InMemoryTaskManager);
    }

}