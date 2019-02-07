package format;

import java.util.*;
import java.util.List;

abstract public class PBMImage {
    List<String> comment = new ArrayList<>();
    int width;
    int height;
    int maxColor;

    List<Integer> bodyValues = new LinkedList<>();

    public String getSubtypeName() {
        return "";
    }

    public void addComment(String comment) {
        this.comment.add(comment);
    }

    public void setImageWidth(String width) {
        this.width = Integer.parseInt(width);
    }

    public void setImageHeight(String height) {
        this.height = Integer.parseInt(height);
    }

    public void setMaxColor(String maxColor) {
        this.maxColor = Integer.parseInt(maxColor);
    }

    public void addBodyValue(String bodyValue) {
        this.bodyValues.add(Integer.parseInt(bodyValue));
    }

    protected int getInvertColor() {
        return 0;
    }

    public void invert() {
        int invertColor = this.getInvertColor();
        List<Integer> newBodyValues = new LinkedList<>();
        for (Integer bodyValue : this.bodyValues) {
            newBodyValues.add(Integer.valueOf(Math.abs(invertColor - bodyValue)));
        }
        this.bodyValues = newBodyValues;
    }

    public void rotate() {
        Collections.reverse(this.bodyValues);
    }
}
