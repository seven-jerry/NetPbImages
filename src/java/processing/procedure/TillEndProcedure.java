package processing.procedure;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import processing.validation.ICursor;
import processing.build.PBMImageBuilder;
import util.DomUtil;

/**
 * this class adds the xml children until the end of the file
 */


public class TillEndProcedure extends AbstractProcedure {
    private NodeList children;

    @Override
    public void proccessXMLElement(final Element procedureElement) {
        DomUtil.trimWhitespace(procedureElement);
        children = procedureElement.getChildNodes();

        for (int i = 0; i < children.getLength(); ++i) {
            Element child = (Element) children.item(i);
            PBMImageBuilder.builder().addContentSectionElement(child);
        }
        PBMImageBuilder.builder().putProcedure(procedureElement.getAttribute("docId"), this);
    }

    @Override
    public void proccesChar(ICursor cursor, String nextCharInFile) {
        for (int i = 0; i < children.getLength(); i++) {
            Element child = (Element) children.item(i);
            PBMImageBuilder.builder().addContentSectionElement(child);
        }
        cursor.nextValidator();
        cursor.performChanges();
    }
}
