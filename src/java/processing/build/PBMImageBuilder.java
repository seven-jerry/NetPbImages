package processing.build;

import configuration.loader.IContentSectionAccess;
import configuration.loader.IContentSectionLoader;
import configuration.loader.IPreSectionAccess;
import filereader.ICharFileReaderCallback;
import filereader.IFileReader;
import format.PBMImage;
import org.w3c.dom.Element;
import configuration.loader.IPreSectionLoader;
import processing.datatype.CharType;
import processing.procedure.AbstractProcedure;
import processing.datatype.subcontent.SubContent;
import processing.validation.ICharContentProccessor;
import processing.validation.ICursor;
import processing.validation.TargetKey;
import util.ReflectionUtil;

import java.io.File;
import java.util.*;

public class PBMImageBuilder implements IPBMImageBuilderConstructor, IPreSectionAccess, IContentSectionAccess, ICharContentProccessor {
    //Configuration
    private IPreSectionLoader preSectionLoader;
    private IContentSectionLoader contentSectionLoader;
    private File configFile;

    // content types
    private HashMap<String, TargetKey> targetKeys = new HashMap<>();
    private HashMap<String, CharType> variabes = new HashMap<>();
    private HashMap<String, SubContent> subcontent = new HashMap<>();
    private HashMap<String, AbstractProcedure> procedures = new HashMap<>();

    // content reading
    private IFileReader fileReader;
    private List<ICharContentProccessor> charContentList = new ArrayList<ICharContentProccessor>();
    private Iterator<ICharContentProccessor> charContentIterator;
    private ICharContentProccessor contentValidator;

    //create image object
    private PBMImage destinationImage;
    private PBMImageBuildDelegate buildDelegate;

    // debug
    String debugString = "";
    //inner classes
    private FileReaderCallback fileReadCallback = new FileReaderCallback();
    private ContentReadCursor cursor = new ContentReadCursor();

    //singleton
    private static PBMImageBuilder sharedInstance = new PBMImageBuilder();

    public static PBMImageBuilder builder() {
        return PBMImageBuilder.sharedInstance;
    }

    private PBMImageBuilder() {
    }

    // construction access
    public IPBMImageBuilderConstructor getConstructor() {
        return this;
    }

    @Override
    public void setPreSectionLoader(IPreSectionLoader configFileReader) {
        this.preSectionLoader = configFileReader;
    }

    @Override
    public void setContentSectionLoader(IContentSectionLoader configFileReader) {
        this.contentSectionLoader = configFileReader;
    }

    public void addContentSectionElement(Element node) {
        this.contentSectionLoader.addElement(node);
    }

    public void changeConfigFilePath(final String configFilePath) {
        String filePath = Objects.requireNonNull(configFilePath, "config file path mus not be null");
        this.configFile = new File(filePath);
        this.contentSectionLoader.setConfigFile(this.configFile);
        this.contentSectionLoader.setup();
    }

    @Override
    public void setFileReader(IFileReader reader) {
        this.fileReader = reader;
    }

    @Override
    public void setConfigFilePath(final String configFilePath) {
        String filePath = Objects.requireNonNull(configFilePath, "config file path mus not be null");
        this.configFile = new File(filePath);
    }

    @Override
    public void setBuildDelegate(PBMImageBuildDelegate delegate) {
        this.buildDelegate = delegate;
    }

    @Override
    public void setup() {
        Objects.requireNonNull(this.configFile, "the config file must be set");
        Objects.requireNonNull(this.preSectionLoader, "the preSectionLoader must be set");
        Objects.requireNonNull(this.fileReader, "the fileReader must be set");
        Objects.requireNonNull(this.contentSectionLoader, "the contentSectionLoader must be set");

        this.preSectionLoader.setConfigFile(this.configFile);
        this.preSectionLoader.setup();
        fileReader.setupFile();
        this.contentSectionLoader.setConfigFile(this.configFile);
        this.contentSectionLoader.setInputFileReader(this.fileReader);
        this.contentSectionLoader.setup();

    }

    @Override
    public void execute() {
        this.cursor.nextValidatorList();
        this.cursor.nextChar();
        this.cursor.performChanges();
    }

    // create image object
    public void setTargetImage(PBMImage image) {
        this.destinationImage = image;
    }

    public PBMImage getTargetImage() {
        return this.destinationImage;
    }

    public PBMImageBuildDelegate getTargetImageDelegate() {
        return this.buildDelegate;
    }

    // content type methods
    public String getVariableContent(String docId) {
        return this.variabes.get(docId).getString();
    }

    @Override
    public void putTargetKey(String docId, TargetKey targetKey) {
        this.targetKeys.put(docId, targetKey);
    }

    @Override
    public void putVariable(String docId, CharType charType) {
        this.variabes.put(docId, charType);
    }

    @Override
    public void putProcedure(String docId, AbstractProcedure procedure) {
        this.procedures.put(docId, procedure);
    }

    @Override
    public void putSubContent(String docId, SubContent subContent) {
        this.subcontent.put(docId, subContent);
    }


    @Override
    public SubContent getSubContent(String docId) {
        SubContent subContent = this.subcontent.get(docId);
        this.subcontent.remove(docId);
        return subContent;
    }

    @Override
    public void setCharValidator(ICharContentProccessor validator) {
        this.contentValidator = validator;
    }

    @Override
    public boolean hasCharValidator() {
        return this.contentValidator != null;
    }

    @Override
    public void updateDocId(String docId, String value) {
        this.variabes.put(docId, new CharType(value));
        this.updateTargetDocId(docId, value);
    }

    private void updateTargetDocId(String docId, String value) {
        try {
            TargetKey targetKey = this.targetKeys.get(docId);
            ReflectionUtil.callTargetAnnotatedMethod(this.buildDelegate, targetKey);

        } catch (NullPointerException e) {

        }
    }

    // file read lifecycle
    public void validationError(){
            System.out.println(this.debugString);
            System.exit(-1);
    }
    @Override
    public void proccesChar(ICursor cursor, String nextCharacter) {
        System.exit(0);
    }

    private void setupContentIterator() {
        this.charContentList = new ArrayList<ICharContentProccessor>();
        this.charContentList.addAll(procedures.values());
        this.charContentList.addAll(subcontent.values());
        this.charContentList.add(this.contentValidator);
        this.charContentList.add(this);

        //   System.out.println(this.charContentList);
        this.charContentIterator = this.charContentList.iterator();
    }

    private void handleFileReadError() {
        this.cursor.currentChar = "";
        this.cursor.currentContentIterationValidator = new EmptyCharContentProccessor();
        this.contentValidator = null;
    }

    class ContentReadCursor implements ICursor {
        private String currentChar;
        private ICharContentProccessor currentContentIterationValidator;

        @Override
        public void nextChar() {
            PBMImageBuilder.this.fileReader.nextChar(PBMImageBuilder.this.fileReadCallback);
        }

        @Override
        public void nextValidator() {
            this.currentContentIterationValidator = PBMImageBuilder.this.charContentIterator.next();
        }

        @Override
        public void nextValidatorList() {
            PBMImageBuilder.this.contentValidator = null;
            PBMImageBuilder.this.contentSectionLoader.nextCharValidator();
            PBMImageBuilder.this.setupContentIterator();
            this.currentContentIterationValidator = PBMImageBuilder.this.charContentIterator.next();
        }

        @Override
        public void performChanges() {
            this.currentContentIterationValidator.proccesChar(this, this.currentChar);
        }

        @Override
        public void fillValidationList() {
            PBMImageBuilder.this.setupContentIterator();
            this.currentContentIterationValidator = PBMImageBuilder.this.charContentIterator.next();
        }

        private void nextCharFromReader(String readChar) {
            PBMImageBuilder.this.debugString += readChar;
            this.currentChar = readChar;
        }
    }

    class FileReaderCallback implements ICharFileReaderCallback {

        @Override
        public void success(String readCharacter) {
            PBMImageBuilder.this.cursor.nextCharFromReader(readCharacter);
        }

        @Override
        public void failed() {
            PBMImageBuilder.this.handleFileReadError();
        }
    }

    // a empty proccessor, used when the file content has exceeded
    class EmptyCharContentProccessor implements ICharContentProccessor {
        @Override
        public void proccesChar(ICursor cursor, String nextCharacter) {
            System.out.println("reading file has ended");
        }
    }

}
