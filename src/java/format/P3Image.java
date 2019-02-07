package format;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class P3Image extends PBMImage {
    public String getSubtypeName() {
        return "P3";
    }

    @Override
    protected int getInvertColor() {
        return 255;
    }

    @Override
    public void rotate() {
        LinkedList<Integer> newBodyValues = new LinkedList<>();
        Collections.reverse(this.bodyValues);
        Iterator<Integer> it = bodyValues.iterator();
        while (it.hasNext()) {
            //get colors in reverse order
            Integer b = it.next();
            Integer g = it.next();
            Integer r = it.next();
            //put them into order
            newBodyValues.add(r);
            newBodyValues.add(g);
            newBodyValues.add(b);
        }
        this.bodyValues = newBodyValues;
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
