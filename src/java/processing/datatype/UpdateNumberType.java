package processing.datatype;

import processing.build.*;
import processing.validation.ICharValidator;
import processing.validation.ICharCompareCallback;
import processing.validation.ICharContentProccessor;
import processing.validation.ICursor;
import util.CharUtil;
import util.NumberUtil;

/**
 * this class validates a certain character in a given range
 * this should be the last validator as it tell the cursor to load the nex Validation list
 * this will update the content for the provided docId
 */

public class UpdateNumberType implements ICharContentProccessor, ICharValidator {
    private String docId;
    private String breakDocId;
    private String rangeDocId;
    private String content = "";

    private ICursor cursor;

    private ICharCompareCallback callback = new CompareCallback();

    public UpdateNumberType(String docId, String rangeDocId, String breakDocId) {
        this.docId = docId;
        this.rangeDocId = rangeDocId;
        this.breakDocId = breakDocId;
    }

    @Override
    public void proccesChar(ICursor cursor, String nextCharacter) {
        this.cursor = cursor;
        String range = PBMImageBuilder.builder().getVariableContent(this.breakDocId);
        CharUtil.inRange(nextCharacter, range, this.callback);
    }

    private void validateNumber() {
        try {
            String rangeVarString = PBMImageBuilder.builder().getVariableContent(this.rangeDocId);
            Integer rangeVar = Integer.parseInt(rangeVarString);
            Integer number = Integer.parseInt(this.content);
            NumberUtil.smallerOrEqual(number,rangeVar,"info : UpdateNumberType - validateNumber : Number is greater that defined range");
            PBMImageBuilder.builder().updateDocId(this.docId, number.toString());
        } catch (Exception e) {
            System.out.println("unable to convert <" + this.content + "> into a integer ");
            PBMImageBuilder.builder().validationError();
        }
    }

    @Override
    public void charInRange(String needle, String docId, String range, ICharCompareCallback callback) {
        CharUtil.inRange(needle, range, callback);
    }

    class CompareCallback implements ICharCompareCallback {

        @Override
        public void success(String content) {
            UpdateNumberType.this.validateNumber();
            UpdateNumberType.this.cursor.nextValidatorList();
            UpdateNumberType.this.cursor.performChanges();
        }

        @Override
        public void fail(String content) {
            UpdateNumberType.this.content += content;
            UpdateNumberType.this.cursor.nextChar();
            UpdateNumberType.this.cursor.performChanges();
        }
    }
}
