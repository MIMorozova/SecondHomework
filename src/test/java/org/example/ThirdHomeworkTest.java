package org.example;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;

public class ThirdHomeworkTest {
    @Test
    public void testIsEven() {
        int n = 8;
        boolean actualResult = FirstHomework.isEven(n);
        boolean expectedResult = n % 2 == 0;
        assertThat(actualResult)
                .as("Проверка числа %d на четность", n)
                .isEqualTo(expectedResult);
    }
    @Test
    public void testRemoveSpecificName() {
        List<String> listNameForTest = List.of("Марк", "Олег", "Марксим", "Александр", "Михаил");
        String removeName = "Марк";
        List<String> actualResult = FirstHomework.removeSpecificName(listNameForTest, removeName);
        List<String> expectedResult = List.of("Олег", "Марксим", "Александр", "Михаил");
        assertThat(actualResult)
                .as("Проверка удаления из списка имени %s", removeName)
                .isEqualTo(expectedResult);
    }

    @Test
    public void testCheckAccess(){
        int age = 69;
        String actualResult = FirstHomework.checkAccess(age);
        String expectedResult = "Allowed";
        assertThat(actualResult)
                .as("Проверка на возраст %d", age)
                .isEqualTo(expectedResult);
    }

    @Test
    public void testFindMax(){
        int[] numbers = {3, 8, 2, 15, 6};
        int actualResult = FirstHomework.findMax(numbers);
        int expectedResult = 8;
        assertThat(actualResult)
                .as("Проверка поиска максимального числа в массиве")
                .isEqualTo(expectedResult);
    }
}