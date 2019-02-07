package configuration.loader;

import java.io.File;

public interface IPreSectionLoader {
    void setup();
    void setConfigFile(File configFile);
}
