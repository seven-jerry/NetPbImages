package configuration.loader;

import filereader.IFileReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import processing.build.PBMImageBuilder;
import processing.validation.IContentType;
import util.DomUtil;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;


public class ContentConfiguration implements IContentSectionLoader {
    private IFileReader inputFileReader;
    private ContentElementIterator contentIterator;
    private File configFile;

    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    public void setInputFileReader(IFileReader inputFileReader) {
        this.inputFileReader = inputFileReader;
    }

    @Override
    public void setup() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(this.configFile);
            doc.normalize();
            DomUtil.trimWhitespace(doc.getDocumentElement());

            Element contentElement = (Element) doc.getDocumentElement().getElementsByTagName("content").item(0);
            DomUtil.trimWhitespace(contentElement);
            this.contentIterator = new ContentElementIterator();
            this.contentIterator.setParentElement(contentElement);
        } catch (Exception e) {
            System.out.println("ContentConfiguration - setup : " + e.getMessage());
            System.exit(-1);
        }
    }


    @Override
    public void nextCharValidator() {
        while (this.contentIterator.hasNext() && PBMImageBuilder.builder().hasCharValidator() == false) {
            Element nextContentElement = this.contentIterator.next();
            try {
                IContentType element = (IContentType) Class.forName("configuration.datatype." + nextContentElement.getTagName() + "Type").newInstance();
                element.loadAsContentType(nextContentElement);
            } catch (Exception e) {
                System.out.println("ContentConfiguration - nextCharValidator : " + e.getMessage());
                System.exit(-1);
            }
        }
    }

    @Override
    public void addElement(Element element) {
        this.contentIterator.addNode(element);
    }


}
