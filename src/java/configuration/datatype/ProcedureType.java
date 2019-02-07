package configuration.datatype;

import org.w3c.dom.Element;
import processing.validation.ICharCompareCallback;
import processing.validation.IContentType;
import processing.procedure.AbstractProcedure;
import processing.validation.ICharValidator;
import util.ReflectionUtil;

public class ProcedureType implements IContentType,ICharValidator {
    @Override
    public void loadAsContentType(Element xmlElement) {
        String className = "processing.procedure." +xmlElement.getAttribute("className")+"Procedure";
        AbstractProcedure procedure = (AbstractProcedure) ReflectionUtil.createNewInstance(className);
        procedure.proccessXMLElement(xmlElement);
    }

    @Override
    public void charInRange(String needle, String docId, String range, ICharCompareCallback callback) {
        new processing.datatype.CharType(range).proccesChar(needle,callback);
    }
}