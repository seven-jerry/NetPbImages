package configuration.datatype;


import org.w3c.dom.Element;
import processing.validation.ICharCompareCallback;
import processing.build.PBMImageBuilder;
import processing.validation.IContentType;
import processing.validation.ICharValidator;

public class CharType implements IContentType,ICharValidator {
    @Override
    public void loadAsContentType(Element xmlElement) {
        PBMImageBuilder.builder().setCharValidator(new  processing.datatype.CharType(xmlElement.getAttribute("range")));
    }

    @Override
    public void charInRange(String needle, String docId, String range, ICharCompareCallback callback) {
        new  processing.datatype.CharType(range).proccesChar(needle,callback);
    }
}
