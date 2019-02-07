package configuration.loader;

import filereader.IFileReader;
import org.w3c.dom.Element;

import java.io.File;

public interface IContentSectionLoader {
    void setup();
    void setInputFileReader(IFileReader inputFileReader);
    void setConfigFile(File configFile);
    void nextCharValidator();
    void addElement(Element element);

}
