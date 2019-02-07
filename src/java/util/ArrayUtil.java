package util;

public class ArrayUtil {
    public static void requireLength(Object[] objects,int length) throws Exception{
        if(objects.length < 2){
            throw new Exception("Array "+objects +" was expected to have length "+length);
        }
    }
}
