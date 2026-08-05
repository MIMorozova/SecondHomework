package org.example;
import java.util.ArrayList;
import java.util.List;

public class FirstHomework {
    //задание 1
    public static boolean isEven(int n) {
        if (n % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

// public static boolean isEven(int n) {
//      return n%2==0
// }


    //задание 2
    public static String checkAccess(int age) {
        if (age > 18) {
            return "Allowed";
        } else {
            return "Denied";
        }
    }

    //задание 3

    public static boolean isPositive(int n) {
        boolean comparison = (n >= 0) ? true : false;
        return comparison;
    }

    // задание 4
    public static String getGrade(int score){
        if (score >=0 && score <= 20) {
            return "E";
        } else if (score >= 21 && score <= 40) {
            return "D";
        } else if (score >= 41 && score <= 60) {
            return "С";
        } else if (score >= 61 && score <= 80) {
            return "B";
        } else if (score >= 81 && score <= 100) {
            return "A";
        }
        else {
            return "Error";
        }
    }

    // задание 5
    public static String blastOff(int start){
        String result = "";
        for (int i = start; i >=1; i--){
            result = result + i + " ";
        }
        return result + "Поехали!";

    }

    // задание 6
    public static int sumToN(int n){
        int resultSum = 0;
        for (int i = 1; i <=n; i++){
            resultSum = resultSum+i;
        }
        return resultSum;
    }

    // задание 7
    public static boolean hasBug(String[] messages){
        for (String element : messages){
            if (element.equalsIgnoreCase("bug")){
                return true;
            }
        }
        return false;
    }

    // задание 8
    public static String getEvenInRange(int start, int end){
        String result = "";
        for (int i = start; i <= end; i++){
            if (i%2==0){
                if (result.isEmpty()) {
                    result = result + i;
                }
                else {
                    result = result + " " + i;
                }
            }
        }
        return result;
    }

    // задание 9
    public static int findMax(int[] arr){
        int resultMax = arr[0];
        for (int i = 1; i < arr.length; i++){
            if (resultMax < arr[i]){
                resultMax = arr[i];
            }
        }
        return resultMax;
    }

    // задание 10
    public static String[] reverse(String[] arr){
        String[] result = new String[arr.length];
        for (int i = 0; i < arr.length; i++){
            result[i] = arr[arr.length - (i + 1)];
        }
        return result;
    }

    // задание 11
    public static double calcAverage(List<Integer> list){
        if (list.isEmpty()){
            return 0.0;
        }
        int sum = 0;
        for (int i = 0; i < list.size(); i++){
            sum = sum + list.get(i);
        }
        return (double) sum / list.size();
    }

    // задание 12
    public static List<String> removeSpecificName(List<String> list, String nameToRemove){
        List<String> listNew = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            if (!list.get(i).equals(nameToRemove)){
                listNew.add(list.get(i));
            }
        }
        return listNew;
    }
}

