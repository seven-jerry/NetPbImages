package processing.datatype;

import processing.validation.ICharCompareCallback;
import processing.validation.ICharContentProccessor;
import processing.validation.ICursor;
import util.CharUtil;

/**
 * this class validates a certain character in a given range
 * it will ignore each valid char and then move to the next validator
 */


public class OptionalChartype implements ICharContentProccessor, ICharCompareCallback {
    private String range;
    private ICursor cursor;

    public OptionalChartype(String rangeString) {
        this.range = rangeString;
    }


    public String getString() {
        return this.range;
    }

    @Override
    public void proccesChar(ICursor cursor, String nextCharacter) {
        this.cursor = cursor;
        CharUtil.inRange(nextCharacter, range, this);
    }

    // callback from CharUtil
    @Override
    public void success(String content) {
        this.cursor.nextChar();
        this.cursor.performChanges();
    }

    @Override
    public void fail(String content) {
        this.cursor.nextValidatorList();
        this.cursor.performChanges();
    }

}
