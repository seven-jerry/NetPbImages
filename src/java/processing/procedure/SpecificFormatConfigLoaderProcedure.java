package processing.procedure;

import org.w3c.dom.Element;
import processing.build.PBMImageBuilder;

/*
 *
 * loads a more specific configuration file
 * */
public class SpecificFormatConfigLoaderProcedure extends AbstractProcedure {
    @Override
    public void proccessXMLElement(final Element procedureElement) {
        PBMImageBuilder builder = PBMImageBuilder.builder();
        String filePath = builder.getVariableContent(procedureElement.getAttribute("keyId"));
        filePath += "_format_config.xml";
        builder.changeConfigFilePath(filePath);
    }
}
