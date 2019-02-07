package configuration.datatype;

import org.w3c.dom.Element;
import processing.build.PBMImageBuilder;
import processing.validation.IContentType;

public class SetVarType implements IContentType {
    @Override
    public void loadAsContentType(Element xmlElement) {
        String range =  PBMImageBuilder.builder().getVariableContent(xmlElement.getAttribute("rangeVar"));
        String docId = xmlElement.getAttribute("docId");
        PBMImageBuilder.builder().setCharValidator(new  processing.datatype.UpdateCharType(docId,range));
    }
}