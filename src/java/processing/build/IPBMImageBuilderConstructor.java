package processing.build;

import configuration.loader.IPreSectionLoader;
import configuration.loader.IContentSectionLoader;
import filereader.IFileReader;


public interface IPBMImageBuilderConstructor {
    void setPreSectionLoader(IPreSectionLoader configFileReader);

    void setContentSectionLoader(IContentSectionLoader configFileReader);

    void setFileReader(IFileReader reader);

    void setConfigFilePath(final String configFilePath);

    void setBuildDelegate(PBMImageBuildDelegate delegate);

    void setup();

    void execute();
}