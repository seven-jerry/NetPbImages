package filereader;


public interface IFileReader {
    String nextChar(ICharFileReaderCallback fileReadCallback);
    void setupFile();
}
