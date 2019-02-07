package configuration.loader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import processing.build.PBMImageBuilder;
import processing.validation.TargetKey;
import processing.datatype.CharType;
import processing.procedure.AbstractProcedure;
import util.CharUtil;
import util.DomUtil;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.*;

/*
 * this loads the configuration for the pre section
 *
 * */
public class PreSectionConfiguration implements IPreSectionLoader {
    private File configFile;


    @Override
    public void setup() {
        Objects.requireNonNull(this.configFile, "config file must not be null");
        this.setupPreSection();
    }

    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    private final void setupPreSection() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(this.configFile);
            doc.normalize();
            DomUtil.trimWhitespace(doc.getDocumentElement());
            Element preElement = (Element) doc.getDocumentElement().getElementsByTagName("pre").item(0);
            //setup target keys
            Element targetKeys = (Element) preElement.getElementsByTagName("keys").item(0);
            this.setupPreTargetKeys(targetKeys.getElementsByTagName("key"));
            //setup variables
            Element variables = (Element) preElement.getElementsByTagName("variables").item(0);
            this.setupPreVariables(variables.getElementsByTagName("Char"));
            this.setupSystemVariables();
            //setup procedures
            Element procedures = (Element) preElement.getElementsByTagName("procedures").item(0);
            this.setupProcedures(procedures.getElementsByTagName("Procedure"));

        } catch (Exception e) {
            System.out.println("ContentConfiguration - setup : " + e.getMessage());
            System.exit(-1);
        }

    }

    /* Setup Target Keys */
    private final void setupPreTargetKeys(final NodeList keys) throws ParserConfigurationException, SAXException {
        for (int i = 0; i < keys.getLength(); i++) {
            Element key = (Element) keys.item(i);
            String docId = key.getAttribute("docId");
            String targetKey = key.getAttribute("targetKey");
            this.addTargetKey(docId, targetKey);
        }
    }

    private final void addTargetKey(final String docId, final String targetKey) {
        try {
            Objects.requireNonNull(docId);
            Objects.requireNonNull(targetKey);
            this.getImageBuilder().putTargetKey(docId, TargetKey.getEnum(targetKey));
        } catch (NullPointerException ex) {
            System.out.println("Failed to add target key: docId:<" + docId + "> targetKey:<" + targetKey + ">");
        } catch (IllegalArgumentException ex) {
            System.out.println("Could not convert targetKey into enum key. givenKey:<" + targetKey + ">");
        }
    }

    /* Setup Variables */
    private final void setupPreVariables(final NodeList variables) {
        for (int i = 0; i < variables.getLength(); i++) {
            Element var = (Element) variables.item(i);
            String docId = var.getAttribute("docId");
            String value = var.getAttribute("range");
            this.addVariable(docId, value);
        }
    }

    private void setupSystemVariables() {
        this.addVariable("whitespace", CharUtil.whitespace());
        this.addVariable("maxIntegerValue", Integer.valueOf(Integer.MAX_VALUE).toString());

    }

    private final void addVariable(final String docId, final String value) {
        try {
            Objects.requireNonNull(docId);
            Objects.requireNonNull(value);
            this.getImageBuilder().putVariable(docId, new CharType(value));
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException - Failed to add variable key: docId:<" + docId + "> value:<" + value + ">");
        } catch (IllegalArgumentException ex) {
            System.out.println("IllegalArgumentException - Could not convert variable into chartype. value:<" + value + ">");
        }
    }

    /* Setup Procedures */
    private final void setupProcedures(final NodeList procedures) {
        for (int i = 0; i < procedures.getLength(); i++) {
            Element procedure = (Element) procedures.item(i);
            String docId = procedure.getAttribute("docId");
            String className = procedure.getAttribute("className");
            this.addProcedure(docId, className, procedure);
        }
    }
    private final void addProcedure(final String docId, final String className, final Element procedureElement) {
        try {
            Objects.requireNonNull(docId);
            Objects.requireNonNull(className);

            Class<?> procedureClass = Class.forName("processing.procedure." + className + "Procedure");
            AbstractProcedure procedure = (AbstractProcedure) procedureClass.newInstance();
            procedure.setDocId(docId);
            procedure.proccessXMLElement(procedureElement);
            PBMImageBuilder.builder().putProcedure(docId, procedure);
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException - Failed to add procedure: docId:<" + docId + "> className:<" + className + ">");
        } catch (IllegalArgumentException ex) {
            System.out.println("IllegalArgumentException - Could not convert className into procedure. className:<" + className + ">");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException - Could not convert procedure into class. className:<" + className + ">");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private IPreSectionAccess getImageBuilder() {
        return PBMImageBuilder.builder();
    }
}
