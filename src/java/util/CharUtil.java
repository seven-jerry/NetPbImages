package util;

import processing.validation.ICharCompareCallback;

import java.util.Objects;

public class CharUtil {

    public static String whitespace(){
        char space = 20;
        char tab = 9;
        char lf = 0xA;
        char lt =  0xB;
        char cr = 0xD;
        return String.valueOf(new char[]{' ',space,tab,lf,lt,cr});
    }

    public static void inRange(String needle, String range, ICharCompareCallback callback){
        Objects.requireNonNull(needle);
        Objects.requireNonNull(range);
        if(range.indexOf(needle) != -1){
            callback.success(needle);
            return;
        }
        callback.fail(needle);
    }
}
