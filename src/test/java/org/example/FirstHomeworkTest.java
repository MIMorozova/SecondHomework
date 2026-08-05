package org.example;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FirstHomeworkTest {
    @BeforeEach //
    void printBefore() {

        System.out.println("========================Test method start");
    }

    @Test
    public void testIsEven() {
        Random random = new Random(); //рандомное значение (генерация)
        int randomValue = random.nextInt(100) + 1; // получаем случайное число от 1 до 100
        boolean result = FirstHomework.isEven(randomValue);
        boolean expected = randomValue % 2 == 0;
        assertTrue(expected == result); // лучше заменить на assertEquals(expected, result);
    }

    @AfterEach
    void printAfter() {
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // задание 1.2
    @Test
    public void testCheckAccess() {
        Random random = new Random(); //рандомное значение (генерация)
        for (int i = 0; i < 20; i++) {
        int randomCheckAccess = random.nextInt(100);
        String resultCheckAccess = FirstHomework.checkAccess(randomCheckAccess);
        String expectedCheckAccess;
        if (randomCheckAccess > 18) {
            expectedCheckAccess = "Allowed";
        } else {
            expectedCheckAccess = "Denied";
        }
        assertEquals(expectedCheckAccess,resultCheckAccess);
        }
    }

    // задание 1.3
    static ArrayList<Integer> provideScores() { //создание метода-источника (откуда берем данные)
        Random scoreGenerator  = new Random(); //рандомное значение (генерация)
        ArrayList<Integer> generatedScores = new ArrayList<>(); //объект, куда добавим случайные оценки
        for (int i = 0; i < 10; i++){
            int generatedScore = scoreGenerator.nextInt(101);
            generatedScores.add(generatedScore); //положили значиния в список
        }
        return generatedScores;
    }

    @ParameterizedTest
    @MethodSource("provideScores")
    public void testGetGrade(int score){ // параметр - значение из метода-источника
        String actualGrade = FirstHomework.getGrade(score); // сохранение результат работы getGrade(score)
        String expectedGrade;
        if (score >=0 && score <= 20) {
            expectedGrade = "E";
        } else if (score >= 21 && score <= 40) {
            expectedGrade = "D";
        } else if (score >= 41 && score <= 60) {
            expectedGrade = "С";
        } else if (score >= 61 && score <= 80) {
            expectedGrade = "B";
        } else if (score >= 81 && score <= 100) {
            expectedGrade = "A";
        }
        else {
            expectedGrade = "Error";
        }
        assertEquals(expectedGrade,actualGrade);
    }

}