import processing.build.PBMImageBuildDelegate;
import processing.build.PBMImageBuilder;
import processing.build.PBMImageFileWriter;
import configuration.loader.ContentConfiguration;
import configuration.loader.PreSectionConfiguration;
import filereader.CharFileReader;
import util.ArrayUtil;
import util.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;

        /*
         * this program reads in pbm images(p1,p2,p3)
         * and applies rotation and color inversion
         *
         * it uses xml files that define the structure of the file
         *
         * java -jar pbm-tool <input-file> -operation1 -operation2 -operation3 <output-file>
         * */

public class Main {
    public static void main(String[] args) {

        // validate arguments
        String inputFilePath = Main.getInputFilePathArg(args);
        String outputFilePath = Main.getOutputFilePathArg(args);
        List<String> actions = Main.getActionParamArgs(args);

        //initialization
        CharFileReader reader = new CharFileReader();
        PreSectionConfiguration configFile = new PreSectionConfiguration();
        ContentConfiguration sectionConfig = new ContentConfiguration();
        PBMImageBuilder imageBuilder = PBMImageBuilder.builder();

        // setup objects
        reader.getConstructor().setFilePath(inputFilePath);
        imageBuilder.getConstructor().setPreSectionLoader(configFile);
        imageBuilder.getConstructor().setContentSectionLoader(sectionConfig);
        imageBuilder.getConstructor().setFileReader(reader);
        imageBuilder.getConstructor().setConfigFilePath("pbm_header_format_config.xml");
        imageBuilder.getConstructor().setBuildDelegate(new PBMImageBuildDelegate());
        imageBuilder.getConstructor().setup();
        imageBuilder.getConstructor().execute();

        System.out.println("input :\n"+imageBuilder.getTargetImage());
        // apply actions
        Main.callActions(imageBuilder.getTargetImageDelegate(), actions);
        PBMImageFileWriter.writeToFile(PBMImageBuilder.builder().getTargetImage(), outputFilePath);
        System.out.println("output :\n"+imageBuilder.getTargetImage());

    }

    // the first param is expected to be the source image file path
    private static String getInputFilePathArg(final String[] args) {
        try {
            String inputFile = args[0];
            return inputFile;
        } catch (Exception e) {
            System.out.println("the first param must be the input file");
            System.out.println("java -jar pbm-tool <input-file> -operation1 -operation2 -operation3 <output-file>");
            System.exit(-1);
        }
        return null;
    }

    // the last param is expected to be the destination image file path
    private static String getOutputFilePathArg(final String[] args) {
        try {
            ArrayUtil.requireLength(args,2);
            String outFile = args[args.length - 1];
            return outFile;
        } catch (Exception e) {
            System.out.println("the last param must be the output file");
            System.out.println("java -jar pbm-tool <input-file> -operation1 -operation2 -operation3 <output-file>");
            System.exit(-1);
        }
        return null;
    }

    // load the actions (invert,rotate) into a array
    // operations are expected to be between inputfile(firstElement) and outputfile(lastElement)
    private static List<String> getActionParamArgs(final String[] args) {
        List<String> actions = new ArrayList<>();
        try {

            for (int i = 1; i < args.length - 1; i++) {
                actions.add(args[i]);
            }
        } catch (Exception e) {
            System.out.println("the last param must be the output file");
            System.out.println("java -jar pbm-tool <input-file> -operation1 -operation2 -operation3 <output-file>");
            System.exit(-1);
        }
        return actions;
    }

    private static void callActions(Object targetObject, List<String> actions) {
        for (String action : actions) {
            ReflectionUtil.callValueAnnotatedMethod(targetObject, action);
        }
    }
}
