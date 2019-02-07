package util;

public class NumberUtil {
    public static void smallerOrEqual(int number1, int number2,String message){
        if(number1 > number2){
            System.out.println(message);
        }
    }
    public static void requirePositive(int number)throws Exception{
        if(number < 0){
            throw new Exception("number is not positive");
        }
    }
}
