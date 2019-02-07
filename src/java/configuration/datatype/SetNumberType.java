package configuration.datatype;

import org.w3c.dom.Element;
import processing.build.PBMImageBuilder;
import processing.validation.IContentType;

public class SetNumberType implements IContentType {


    @Override
    public void loadAsContentType(Element xmlElement) {
        String docId = xmlElement.getAttribute("docId");
        String rangeDocId = xmlElement.getAttribute("rangeVarId");
        String breakDocId = xmlElement.getAttribute("breakDocId");

        PBMImageBuilder.builder().setCharValidator(new  processing.datatype.UpdateNumberType(docId,rangeDocId,breakDocId));
    }
}