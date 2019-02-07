package processing.build;

import format.PBMImage;
import java.io.*;

/*
* write a PBMImage - object to a file
* */
public class PBMImageFileWriter {
    public static void writeToFile(PBMImage image,String filePath){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath))))

        {
            writer.write(image.toString());
        } catch (UnsupportedEncodingException e) {
           System.out.println("PBMImageFileWriter - writeToFile : UnsupportedEncodingException : "+e.getMessage());
           System.exit(-1);
        } catch (FileNotFoundException e) {
            System.out.println("PBMImageFileWriter - writeToFile : FileNotFoundException : "+e.getMessage());
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("PBMImageFileWriter - writeToFile : IOException : "+e.getMessage());
            System.exit(-1);
        }
    }
}
