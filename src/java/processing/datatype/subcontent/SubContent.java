package processing.datatype.subcontent;

import processing.validation.ICharContentProccessor;
import processing.validation.ICursor;

public class SubContent implements ICharContentProccessor{
    private String content="";

    public void addContent(String content) {
        this.content = this.content.concat(content);
    }
    public String getContent(){
        return  this.content;
    }

    @Override
    public void proccesChar(ICursor cursor, String nextCharacter) {
      this.addContent(nextCharacter);
        cursor.nextValidator();
        cursor.performChanges();
    }
}
