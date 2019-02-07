package configuration.datatype;

import org.w3c.dom.Element;
import processing.validation.ICharCompareCallback;
import processing.build.PBMImageBuilder;
import processing.validation.IContentType;
import processing.validation.ICharValidator;

public class VarType implements IContentType,ICharValidator {
    @Override
    public void loadAsContentType(Element xmlElement) {
        String range =  PBMImageBuilder.builder().getVariableContent(xmlElement.getAttribute("docId"));
        PBMImageBuilder.builder().setCharValidator(new  processing.datatype.CharType(range));
    }
    @Override
    public void charInRange(String needle, String docId, String range, ICharCompareCallback callback) {
        String variableRange =  PBMImageBuilder.builder().getVariableContent(docId);
        new  processing.datatype.CharType(variableRange).proccesChar(needle,callback);
    }
}