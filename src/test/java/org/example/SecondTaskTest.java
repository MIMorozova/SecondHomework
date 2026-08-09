package org.example;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@Tag("secondTask")

public class SecondTaskTest {
    // @Test
    @RepeatedTest(10)
    public void testIsEven() {
        Random numberGererator = new Random(); //рандомное значение (генерация)
        int randomValue = numberGererator.nextInt(100) + 1; // получаем случайное число от 1 до 100
        boolean actualResult = FirstHomework.isEven(randomValue);
        boolean expectedResult = randomValue % 2 == 0;
        assertThat(actualResult)
                .as("Проверка числа %d на четность", randomValue)
                .isEqualTo(expectedResult);
    }

    @RepeatedTest(10)
    public void testIsPositive() {
        Random numberGererator = new Random(); //рандомное значение (генерация)
        int randomValue = numberGererator.nextInt(201) - 100; // получаем случайное число от -100 до 100
        boolean actualResultIsPositive = FirstHomework.isPositive(randomValue);
        boolean expectedResultIsPositive = randomValue >= 0;
        assertThat(actualResultIsPositive)
                .as("Проверка положительности числа %d", randomValue)
                .isEqualTo(expectedResultIsPositive);
    }

    @RepeatedTest(10)
    public void testCheckAccess() {
        Random ageGenerator = new Random(); //рандомное значение (генерация)
        int randomAge = ageGenerator.nextInt(101);
        String resultCheckAccess = FirstHomework.checkAccess(randomAge);
        String expectedCheckAccess;
        if (randomAge > 18) {
            expectedCheckAccess = "Allowed";
        } else {
            expectedCheckAccess = "Denied";
        }
        assertThat(resultCheckAccess)
                .as("Проверка доступа с возрастом %d", randomAge)
                .isEqualTo(expectedCheckAccess);
    }

    @RepeatedTest(10)
    public void testBlastOff() {
        Random numberGenerator = new Random();
        int startValue = numberGenerator.nextInt(10) + 1;
        String actualResultBlast = FirstHomework.blastOff(startValue);
        String expectedResultBlast = "";
        for (int i = startValue; i >= 1; i--) {
            expectedResultBlast = expectedResultBlast + i + " ";
        }
        expectedResultBlast = expectedResultBlast + "Поехали!";
        assertThat(actualResultBlast)
                .as("Проверка запуска счетчика с значения %d", startValue)
                .isEqualTo(expectedResultBlast);


    }

    // @RepeatedTest
    @RepeatedTest(10)
    public void testSumToN() {
        Random numberGenerator = new Random();
        int startValue = numberGenerator.nextInt(5) + 1;
        int actualResultForSum = FirstHomework.sumToN(startValue);
        int expectedResultForSum = 0;
        for (int i = 1; i <= startValue; i++) {
            expectedResultForSum = expectedResultForSum + i;
        }
        assertThat(actualResultForSum)
                .as("Проверка суммы всех целых числе от 1 до %d", startValue)
                .isEqualTo(expectedResultForSum);
    }

    @RepeatedTest(10)
    public void testHasBug() {
        Random scenarioGenerator = new Random();
        boolean shouldIncludeBug = scenarioGenerator.nextBoolean();
        String[] messageWithBug = new String[]{"Breakfast", "bug", "FOREST"}; //входной массив
        String[] messageWithoutBug = new String[]{"Sun", "FLOWERS", "Children"};//входной массив
        String[] seclectedMessage;
        boolean expectedResultHasBug;
        if (shouldIncludeBug) {
            seclectedMessage = messageWithBug;
            expectedResultHasBug = true;
        } else {
            seclectedMessage = messageWithoutBug;
            expectedResultHasBug = false;
        }
        boolean actualResultHasBug = FirstHomework.hasBug(seclectedMessage);
        assertThat(actualResultHasBug)
                .as("Проверка наличия значения bug, ожидаемый результат: true")
                .isEqualTo(expectedResultHasBug);
    }

    @RepeatedTest(10)
    public void testFindMax() {
        Random numberGenerator = new Random();
        int[] generatedNumbers = new int[5];  // создаем пустой массив для хранения 5 элементов
        for (int i = 0; i < generatedNumbers.length; i++) {
            generatedNumbers[i] = numberGenerator.nextInt(100); // наполнение пустого массив
        }
        int actualResultFindMax = FirstHomework.findMax(generatedNumbers);
        int expectedResultFindMax = generatedNumbers[0]; // принимаем что первый элемент массива это временный максимум
        for (int i = 1; i < generatedNumbers.length; i++) {
            if (generatedNumbers[i] > expectedResultFindMax) {
                expectedResultFindMax = generatedNumbers[i];
            }
        }
        assertThat(actualResultFindMax)
                .as("Проверка наличия максимального значения в массиве %s", Arrays.toString(generatedNumbers))
                .isEqualTo(expectedResultFindMax);
    }


    @RepeatedTest(10)
    public void testReverse() {
        String[] testDataForReverse = new String[]{"Apple", "Watermelon", "Limon"}; //входной массив
        String[] actualResultReverse = FirstHomework.reverse(testDataForReverse); //результат reverse()
        String[] expectedResultReverse = new String[]{"Limon", "Watermelon", "Apple"}; // ожидаемый массив
        assertThat(actualResultReverse)
                .as("Проверка разворота массива %s", Arrays.toString(testDataForReverse))
                .isEqualTo(expectedResultReverse);
    }

    // @ParameterizedTest
    static ArrayList<Integer> provideScores() { //создание метода-источника (откуда берем данные)
        Random scoreGenerator = new Random(); //рандомное значение (генерация)
        ArrayList<Integer> generatedScores = new ArrayList<>(); //объект, куда добавим случайные оценки
        for (int i = 0; i < 10; i++) {
            int generatedScore = scoreGenerator.nextInt(101);
            generatedScores.add(generatedScore); //положили значиния в список
        }
        return generatedScores;
    }

    @ParameterizedTest
    @MethodSource("provideScores")
    public void testGetGrade(int score) { // параметр - значение из метода-источника
        String actualGrade = FirstHomework.getGrade(score); // сохранение результат работы getGrade(score)
        String expectedGrade;
        if (score >= 0 && score <= 20) {
            expectedGrade = "E";
        } else if (score >= 21 && score <= 40) {
            expectedGrade = "D";
        } else if (score >= 41 && score <= 60) {
            expectedGrade = "С";
        } else if (score >= 61 && score <= 80) {
            expectedGrade = "B";
        } else if (score >= 81 && score <= 100) {
            expectedGrade = "A";
        } else {
            expectedGrade = "Error";
        }
        assertThat(actualGrade)
                .as("Проверка расчета грейда исходя из %d ", score)
                .isEqualTo(expectedGrade);
    }

    static ArrayList<Arguments> provideRanges() {
        Random rangeGenerator = new Random(); //рандомное значение (генерация)
        ArrayList<Arguments> generatedRanges = new ArrayList<>(); //объект, куда добавим случайные диапазон
        for (int i = 0; i < 10; i++) {
            int generatedStart = rangeGenerator.nextInt(100);
            int generatedEnd = rangeGenerator.nextInt(100 - generatedStart) + generatedStart + 1; //условие чтобы end точно был больше start
            generatedRanges.add(Arguments.of(generatedStart, generatedEnd));
        }
        return generatedRanges;
    }

    @ParameterizedTest
    @MethodSource("provideRanges")
    public void testGetEvenInRange(int start, int end) {
        String actualResultEvenRange = FirstHomework.getEvenInRange(start, end);
        String expectedResultEvenRange = "";
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (expectedResultEvenRange.isEmpty()) {
                    expectedResultEvenRange = expectedResultEvenRange + i;
                } else {
                    expectedResultEvenRange = expectedResultEvenRange + " " + i;
                }
            }
        }
        assertThat(actualResultEvenRange)
                .as("Проверка четных чисел в диапазоне от %d до %d", start, end)
                .isEqualTo(expectedResultEvenRange);
    }

    static ArrayList<Arguments> provideNumberLists() {
        Random numberGenerator = new Random();
        ArrayList<Arguments> generatedNumberLists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<Integer> generatedNumbers = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                int generatedNumber = numberGenerator.nextInt(100);
                generatedNumbers.add(generatedNumber);
            }
            generatedNumberLists.add(Arguments.of(generatedNumbers));
        }

        return generatedNumberLists;
    }

    @ParameterizedTest
    @MethodSource("provideNumberLists")
    public void testCalcAverage(List<Integer> numbers) {
        double actualCalcAverage = FirstHomework.calcAverage(numbers);
        double testSum = 0;
        for (int i = 0; i < numbers.size(); i++) {
            testSum = testSum + numbers.get(i);
        }
        double expectedCalcAverage = testSum / numbers.size();
        assertThat(actualCalcAverage)
                .as("Проверка среднего арифметического всех чисел в списке %s", numbers)
                .isCloseTo(expectedCalcAverage, within(0.0001));
    }

    static ArrayList<Arguments> provideName() {
        Random nameGenerator = new Random();
        List<String> listNameForTest = List.of("Мария", "Ольга", "Маргарита", "Александра", "Аделина");
        ArrayList<Arguments> generatedNames = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<String> generatedNameList = new ArrayList<>(); // создаем пустой список
            for (int k = 0; k < 5; k++) {
                int generatedNameIndex = nameGenerator.nextInt(listNameForTest.size());
                String nameTest = listNameForTest.get(generatedNameIndex);
                generatedNameList.add(nameTest);
            }
            int nameRemoveIndex = nameGenerator.nextInt(generatedNameList.size()); //случайный индекс удаляемого имени
            String generatedNameToRemove = generatedNameList.get(nameRemoveIndex); //по индексу получаем имя для удаления
            generatedNames.add(Arguments.of(generatedNameList, generatedNameToRemove));
        }
        return generatedNames;
    }

    @ParameterizedTest
    @MethodSource("provideName")
    public void testRemoveSpecificName(List<String> generatedNameList, String generatedNameToRemove) {
        List<String> actualRemoveSpecificName = FirstHomework.removeSpecificName(generatedNameList, generatedNameToRemove);
        List<String> expectedRemoveSpecificName = new ArrayList<>();
        for (int i = 0; i < generatedNameList.size(); i++) {
            if (!generatedNameList.get(i).equals(generatedNameToRemove)) {
                expectedRemoveSpecificName.add(generatedNameList.get(i));
            }
        }
        assertThat(actualRemoveSpecificName)
                .as("Проверка удаления имени %s из списка %s", generatedNameToRemove, generatedNameList)
                .isEqualTo(expectedRemoveSpecificName);
    }
}