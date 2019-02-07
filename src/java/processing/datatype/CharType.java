package processing.datatype;

import processing.build.PBMImageBuilder;
import processing.validation.ICharCompareCallback;
import processing.validation.ICharContentProccessor;
import processing.validation.ICursor;
import processing.validation.ICharValidator;
import util.CharUtil;

/**
 * this class validates a certain character in a given range
 * this should be the last validator as it tell the cursor to load the nex Validation list
 *
 */
public class CharType implements ICharContentProccessor, ICharCompareCallback, ICharValidator {
    private String range;
    private ICursor cursor;

    public CharType(String rangeString) {
        this.range = rangeString;
    }


    public void proccesChar(String nextCharacter, ICharCompareCallback callback) {
        CharUtil.inRange(nextCharacter, range, callback);
    }

    public String getString() {
        return this.range;
    }

    @Override
    public void proccesChar(ICursor cursor, String nextCharacter) {
        this.cursor = cursor;
        CharUtil.inRange(nextCharacter, range, this);
    }

    @Override
    public void success(String content) {
        this.cursor.nextValidatorList();
        this.cursor.nextChar();
        this.cursor.performChanges();
    }

    @Override
    public void fail(String content) {
        System.out.println("Validation failed on CharType range:<" + this.range + "> content:<" + content + ">");
        PBMImageBuilder.builder().validationError();
    }

    @Override
    public void charInRange(String needle, String docId, String range, ICharCompareCallback callback) {
        CharUtil.inRange(needle, range, callback);
    }


}
