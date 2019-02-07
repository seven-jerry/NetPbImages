package filereader;

import util.NumberUtil;

import java.io.*;
import java.nio.charset.Charset;

        /*
         * this class reads a file char by char
         *
         * */

public class CharFileReader implements IFileReader, ICharFileReaderConstruction {
    Charset encoding = Charset.defaultCharset();
    private File file;
    private BufferedReader reader;

    // construction access
    public ICharFileReaderConstruction getConstructor() {
        return this;
    }

    public void setFilePath(String path) {
        this.file = new File(path);
    }

    public void setupFile() {
        try {
            InputStream in = new FileInputStream(this.file);
            Reader inputReader = new InputStreamReader(in, this.encoding);
            this.reader = new BufferedReader(inputReader);
        } catch (FileNotFoundException e) {
            System.out.println("CharFileReader - FileNotFoundException : File :<" + this.file + "> not found ");
            System.exit(1);
        }
    }

    @Override
    public String nextChar(ICharFileReaderCallback fileReadCallback) {
        String nextCharInFile = null;
        try {
            int nextInt = this.reader.read();
            NumberUtil.requirePositive(nextInt);
            fileReadCallback.success(String.valueOf((char) nextInt));
        } catch (IOException e) {
            System.out.println("CharFileReader - IOException : File :<" + this.file + ">  ");
            System.exit(1);
        } catch (Exception e) {
            fileReadCallback.failed();
        }
        return nextCharInFile;
    }
}
