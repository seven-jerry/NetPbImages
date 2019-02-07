package format;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class P2Image extends PBMImage {
    public String getSubtypeName() {
        return "P2";
    }

    @Override
    protected int getInvertColor() {
        return 255;
    }

    @Override
    public String toString() {
        String imageString = this.getSubtypeName() + "\n";
        for (String comment : this.comment) {
            imageString += comment + "\n";
        }
        imageString += this.width + " " + this.height + "\n";
        imageString += this.maxColor + "\n";
        for (Integer bodyValue : this.bodyValues) {
            imageString += bodyValue;
            imageString += "\n";
        }

        return imageString;
    }
}
