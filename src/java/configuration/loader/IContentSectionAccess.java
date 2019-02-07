package configuration.loader;

import processing.datatype.subcontent.SubContent;
import processing.validation.ICharContentProccessor;

public interface IContentSectionAccess {
    void putSubContent(String docId, SubContent subContent);
    SubContent getSubContent(String docId);
    void updateDocId(String docId, String value);
    void setCharValidator(ICharContentProccessor validator);
    boolean hasCharValidator();
}
