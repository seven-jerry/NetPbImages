package configuration.datatype;

import org.w3c.dom.Element;
import processing.build.PBMImageBuilder;
import processing.validation.IContentType;

public class MultipleOptVarType implements  IContentType {
    @Override
    public void loadAsContentType(Element xmlElement) {
        String range =  PBMImageBuilder.builder().getVariableContent(xmlElement.getAttribute("docId"));
        PBMImageBuilder.builder().setCharValidator(new  processing.datatype.OptionalChartype(range));
    }
}