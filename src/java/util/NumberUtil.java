package util;

import java.util.Optional;

public class NumberUtil {
    public static void smallerOrEqual(int number1, int number2, String message) {
        Optional.of(number1)
                .filter(n -> n > number2)
                .ifPresent((n) -> System.out.println(message));

    }

    public static void requirePositive(int number) throws Exception {
        Optional.of(number)
                .filter(n -> n >= 0)
                .orElseThrow(() -> new Exception("number is not positive"));

    }

}
