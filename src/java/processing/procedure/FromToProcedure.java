package processing.procedure;

import org.w3c.dom.Element;
import processing.build.*;
import processing.validation.ICharCompareCallback;
import processing.validation.ICharContentProccessor;
import processing.validation.ICharValidator;
import processing.validation.ICursor;
import util.DomUtil;

import java.util.Objects;
        /*
         * this class takes all strings from one char to another and proccesses them
         * i.e. comments in files
         * */

public class FromToProcedure extends AbstractProcedure implements ICharContentProccessor {
    private ICharValidator fromChar;
    private String docId;
    String fromDocID;
    String fromRange;
    String content;

    private ICharValidator toChar;
    String toDocId;
    String toRange;

    private ICharValidator continuesWith;
    String continuesWithDocId;
    String continuesWithRange;

    private ICharValidator next;
    String nextDocID;
    String nextRange;
    ICharCompareCallback nextCallback;

    private FromCallback fromCallback = new FromCallback();
    private ToCallback toCallback = new ToCallback();

    @Override
    public void proccessXMLElement(final Element procedureElement) {
        this.docId = procedureElement.getAttribute("docId");
        Element from = (Element) procedureElement.getElementsByTagName("from").item(0);
        Element to = (Element) procedureElement.getElementsByTagName("to").item(0);

        this.processFromElement(from);
        this.processToElement(to);

        this.next = this.fromChar;
        this.nextDocID = this.fromDocID;
        this.nextRange = this.fromRange;
        this.nextCallback = this.fromCallback;
    }

    @Override
    public void proccesChar(ICursor cursor, String nextCharacter) {
        super.proccesChar(cursor, nextCharacter);
        this.next.charInRange(nextCharacter, this.nextDocID, this.nextRange, this.nextCallback);
    }


    private final void processFromElement(final Element from) {
        try {
            Objects.requireNonNull(from);
            DomUtil.trimWhitespace(from);
            Element fromChild = (Element) from.getFirstChild();
            String tagName = fromChild.getTagName();
            this.fromChar = this.objectFromElementName(tagName);
            this.fromDocID = fromChild.getAttribute("docId");
            this.fromRange = fromChild.getAttribute("range");

        } catch (NullPointerException ex) {
            System.out.println("NullPointerException - processFromElement");
        } catch (IllegalArgumentException ex) {
            System.out.println("IllegalArgumentException - processFromElement");
        }
    }

    private final void processToElement(final Element to) {
        try {
            Objects.requireNonNull(to);
            DomUtil.trimWhitespace(to);
            Element fromChild = (Element) to.getFirstChild();
            String tagName = fromChild.getTagName();
            this.toChar = this.objectFromElementName(tagName);
            this.toDocId = fromChild.getAttribute("docId");
            this.toRange = fromChild.getAttribute("range");

        } catch (NullPointerException ex) {
            System.out.println("NullPointerException - processToElement");
        } catch (IllegalArgumentException ex) {
            System.out.println("IllegalArgumentException - processToElement");
        }
    }

    private final ICharValidator objectFromElementName(String elementName) {
        try {
            Objects.requireNonNull(elementName);
            Class characterAccessClass = Class.forName("configuration.datatype." + elementName + "Type");
            return (ICharValidator) characterAccessClass.newInstance();
        } catch (NullPointerException ex) {
            System.out.println("FromToProcedure - objectFromElementName : NullPointerException");
        } catch (IllegalArgumentException ex) {
            System.out.println("FromToProcedure - objectFromElementName : IllegalArgumentException");
        } catch (ClassNotFoundException ex) {
            System.out.println("FromToProcedure : objectFromELementName : Class <processing.contenttype.." + elementName + "Type> not found ");
        } catch (IllegalAccessException e) {
            System.out.println("FromToProcedure - IllegalAccessException");
        } catch (InstantiationException e) {
            System.out.println("FromToProcedure - InstantiationException");
        }
        return null;
    }

    class FromCallback implements ICharCompareCallback {

        @Override
        public void success(String content) {

            FromToProcedure.this.content = content;
            FromToProcedure.this.next = FromToProcedure.this.toChar;
            FromToProcedure.this.nextDocID = FromToProcedure.this.toDocId;
            FromToProcedure.this.nextRange = FromToProcedure.this.toRange;
            FromToProcedure.this.nextCallback = FromToProcedure.this.toCallback;
            FromToProcedure.this.cursor.nextChar();
            FromToProcedure.this.cursor.performChanges();
        }

        @Override
        public void fail(String content) {
            FromToProcedure.this.cursor.nextValidator();
            FromToProcedure.this.cursor.performChanges();

        }
    }

    class ToCallback implements ICharCompareCallback {


        @Override
        public void success(String content) {

            FromToProcedure.this.next = FromToProcedure.this.fromChar;
            FromToProcedure.this.nextDocID = FromToProcedure.this.fromDocID;
            FromToProcedure.this.nextRange = FromToProcedure.this.fromRange;
            FromToProcedure.this.nextCallback = FromToProcedure.this.fromCallback;

            PBMImageBuilder.builder().updateDocId(FromToProcedure.this.docId, FromToProcedure.this.content);
            FromToProcedure.this.content = "";
            FromToProcedure.this.cursor.nextChar();
            FromToProcedure.this.cursor.nextValidator();
            FromToProcedure.this.cursor.performChanges();
        }

        @Override
        public void fail(String content) {
            FromToProcedure.this.content += content;
            FromToProcedure.this.cursor.nextChar();
            FromToProcedure.this.cursor.performChanges();
        }
    }

}
