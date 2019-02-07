package processing.datatype;

import processing.validation.ICharCompareCallback;
import processing.validation.ICharContentProccessor;
import processing.validation.ICursor;
import processing.build.PBMImageBuilder;
import processing.validation.ICharValidator;
import util.CharUtil;

/**
 * this class validates a certain character in a given range
 * this should be the last validator as it tell the cursor to load the nex Validation list
 * this will update the content for the provided docId
 */
public class UpdateCharType implements ICharContentProccessor, ICharCompareCallback, ICharValidator {
    private String range;
    private String docId;
    private ICursor cursor;

    public UpdateCharType(String docId, String rangeString) {
        this.range = rangeString;
        this.docId = docId;
    }

    @Override
    public void proccesChar(ICursor cursor, String nextCharacter) {
        this.cursor = cursor;
        CharUtil.inRange(nextCharacter, range, this);
    }

    public void proccesChar(String nextCharacter, ICharCompareCallback callback) {
        CharUtil.inRange(nextCharacter, range, callback);
    }

    public String getString() {
        return this.range;
    }

    @Override
    public void success(String content) {
        PBMImageBuilder.builder().updateDocId(this.docId, content);
        this.cursor.nextValidatorList();
        this.cursor.nextChar();
        this.cursor.performChanges();
    }

    @Override
    public void fail(String content) {
        System.out.println("Validation failed on UpdateCahrType range:<" + this.range + "< content:<" + content + ">");
        PBMImageBuilder.builder().validationError();

    }

    @Override
    public void charInRange(String needle, String docId, String range, ICharCompareCallback callback) {
        CharUtil.inRange(needle, range, callback);
    }
}
