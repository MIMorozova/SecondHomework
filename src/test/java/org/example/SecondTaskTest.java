package org.example;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


public class SecondTaskTest {
    // @Test
    @Test
    public void testIsEven() {
        Random numberGererator = new Random(); //рандомное значение (генерация)
        int randomValue = numberGererator.nextInt(100) + 1; // получаем случайное число от 1 до 100
        boolean actualResult = FirstHomework.isEven(randomValue);
        boolean expectedResult = randomValue % 2 == 0;
        if (expectedResult == actualResult) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    public void testIsPositive() {
        Random numberGererator = new Random(); //рандомное значение (генерация)
        int randomValue = numberGererator.nextInt(201) - 100; // получаем случайное число от -100 до 100
        boolean actualResultIsPositive = FirstHomework.isPositive(randomValue);
        boolean expectedResultIsPositive = randomValue >= 0;
        if (expectedResultIsPositive == actualResultIsPositive) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
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
        if (expectedCheckAccess.equals(resultCheckAccess)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    public void testBlastOff() {
        Random numberGenerator = new Random();
        int startValue = numberGenerator.nextInt(10) + 1;
        String actualResultBlast = FirstHomework.blastOff(startValue);
        String expectedResultBlast = "";
        for (int i = startValue; i >= 1; i--) {
            expectedResultBlast = expectedResultBlast + i + " ";
        }
        expectedResultBlast = expectedResultBlast + "Поехали!";
        if (expectedResultBlast.equals(actualResultBlast)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }


    }

    // @RepeatedTest
    @RepeatedTest(5)
    public void testSumToN() {
        Random numberGenerator = new Random();
        int startValue = numberGenerator.nextInt(5) + 1;
        int actualResultForSum = FirstHomework.sumToN(startValue);
        int expectedResultForSum = 0;
        for (int i = 1; i <= startValue; i++) {
            expectedResultForSum = expectedResultForSum + i;
        }
        if (expectedResultForSum == actualResultForSum) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @RepeatedTest(3)
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
        if (actualResultHasBug == expectedResultHasBug) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @RepeatedTest(3)
    public void testFindMax() {
        Random numberGenerator = new Random();
        int[] generatedNumbers = new int[5];  // создаем пустой массив для хранения 5 элементов
        for (int i = 0; i < generatedNumbers.length; i++) {
            generatedNumbers[i] = numberGenerator.nextInt(100); // наполнение пустого массива
            System.out.println(generatedNumbers[i]);
        }
        int actualResultFindMax = FirstHomework.findMax(generatedNumbers);
        int expectedResultFindMax = generatedNumbers[0]; // принимаем что первый элемент массива это временный максимум
        for (int i = 1; i < generatedNumbers.length; i++) {
            if (generatedNumbers[i] > expectedResultFindMax) {
                expectedResultFindMax = generatedNumbers[i];
            }
        }
        if (actualResultFindMax == expectedResultFindMax) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @RepeatedTest(3)
    public void testReverse() {
        String[] testDataForReverse = new String[]{"Apple", "Watermelon", "Limon"}; //входной массив
        String[] actualResultReverse = FirstHomework.reverse(testDataForReverse); //результат reverse()
        String[] expectedResultReverse = new String[]{"Limon", "Watermelon", "Apple"}; // ожидаемый массив
        boolean equalValueReverse = true; // общий флаг совпадения
        for (int i = 0; i < actualResultReverse.length; i++) {
            if (!expectedResultReverse[i].equals(actualResultReverse[i])) {
                equalValueReverse = false;
            }
        }
        if (equalValueReverse) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
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
        if (actualGrade.equals(expectedGrade)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    static ArrayList<Arguments> provideRanges() {
        Random rangeGenerator = new Random(); //рандомное значение (генерация)
        ArrayList<Arguments> generatedRanges = new ArrayList<>(); //объект, куда добавим случайные диапазон
        for (int i = 0; i < 5; i++) {
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
        if (actualResultEvenRange.equals(expectedResultEvenRange)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    static ArrayList<Arguments> provideNumberLists() {
        Random numberGenerator = new Random();
        ArrayList<Arguments> generatedNumberLists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
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
        if (Math.abs(expectedCalcAverage - actualCalcAverage) < 0.0001) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    static ArrayList<Arguments> provideName() {
        Random nameGenerator = new Random();
        List<String> listNameForTest = List.of("Мария", "Ольга", "Маргарита", "Александра", "Аделина");
        ArrayList<Arguments> generatedNames = new ArrayList<>();
        for (int i = 0; i < 5; i++) { // цикл для добавления в него 5 наборов данных
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
        if (expectedRemoveSpecificName.equals(actualRemoveSpecificName)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
}