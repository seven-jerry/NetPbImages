package util;

import java.util.Arrays;
import java.util.Optional;

public class ArrayUtil {
    public static void requireLength(Object[] objects,int length) throws Exception{
        Optional.of(objects)
                .filter(o -> o.length > length)
                .orElseThrow(() -> new Exception("Array "+ Arrays.toString(objects) +" was expected to have length "+length));
    }
}
