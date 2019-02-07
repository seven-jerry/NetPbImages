package processing.procedure;

import org.w3c.dom.Element;
import processing.validation.ICharContentProccessor;
import processing.validation.ICursor;

public class AbstractProcedure implements ICharContentProccessor {
    protected String docId;
    protected ICursor cursor;

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public void proccessXMLElement(final Element procedureElement) {

    }

    public void proccesChar(ICursor cursor, String nextCharacter) {
        this.cursor = cursor;
    }
}
