package processing.build;

import format.PBMImage;
import processing.validation.TargetKey;
import util.TargetKeyReciever;
import util.ReflectionUtil;
import util.ValueAnnotation;

public class PBMImageBuildDelegate {

    @TargetKeyReciever(TargetKey.SUBTYPE)
    public void createImageInstance(String subtype) {

        PBMImage image = (PBMImage) ReflectionUtil.createNewInstance("format." + subtype + "Image");
        PBMImageBuilder.builder().setTargetImage(image);

    }

    @TargetKeyReciever(TargetKey.COMMENT)
    public void addComment(String comment) {
        PBMImageBuilder.builder().getTargetImage().addComment(comment);
    }

    @TargetKeyReciever(TargetKey.IMAGE_WIDTH)
    public void setImageWidth(String width) {
        PBMImageBuilder.builder().getTargetImage().setImageWidth(width);
    }

    @TargetKeyReciever(TargetKey.IMAGE_HEIGHT)
    public void setImageHeight(String height) {
        PBMImageBuilder.builder().getTargetImage().setImageHeight(height);
    }

    @TargetKeyReciever(TargetKey.MAX_COLOR_VALUE)
    public void setMaxColor(String maxColor) {
        PBMImageBuilder.builder().getTargetImage().setMaxColor(maxColor);
    }

    @TargetKeyReciever(TargetKey.BODY_VALUE)
    public void addBodyValue(String bodyValue) {
        PBMImageBuilder.builder().getTargetImage().addBodyValue(bodyValue);
    }

    @ValueAnnotation("-rotate")
    public void rotate() {
        PBMImageBuilder.builder().getTargetImage().rotate();
    }

    @ValueAnnotation("-invert")
    public void invert() {
        PBMImageBuilder.builder().getTargetImage().invert();
    }
}
