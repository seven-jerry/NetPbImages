package configuration.datatype;

import org.w3c.dom.Element;
import processing.build.PBMImageBuilder;
import processing.validation.IContentType;

import processing.datatype.subcontent.SubContent;
import processing.datatype.subcontent.state;
import util.ReflectionUtil;
import util.ValueAnnotation;

public class SubContentType implements IContentType{
    private Element xmlElement;
    @Override
    public void loadAsContentType(Element xmlElement) {
        this.xmlElement = xmlElement;
        ReflectionUtil.callValueAnnotatedMethod(this,xmlElement.getAttribute("state"));
    }

    @ValueAnnotation(value = state.START)
    public void startSubContent(){
        String docId = this.xmlElement.getAttribute("docId");
        PBMImageBuilder.builder().putSubContent(docId,new SubContent());
    }

    @ValueAnnotation(value = state.END)
    public void stopSubContent(){
        String docId = this.xmlElement.getAttribute("docId");
        SubContent content = PBMImageBuilder.builder().getSubContent(docId);
        PBMImageBuilder.builder().updateDocId(docId,content.getContent());

    }
}
