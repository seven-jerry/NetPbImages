package format;


public class P1Image extends PBMImage {


    public String getSubtypeName() {
        return "P1";
    }

    @Override
    protected int getInvertColor() {
        return 1;
    }

    @Override
    public String toString() {
        String imageString = this.getSubtypeName() + "\n";
        for (String comment : this.comment) {
            imageString += comment + "\n";
        }
        imageString += this.width + " " + this.height + "\n";
        int counter = 0;
        for (Integer bodyValue : this.bodyValues) {
            imageString += bodyValue;
            counter++;
            if (counter % this.width == 0) {
                imageString += "\n";
            } else {
                imageString += " ";
            }
        }
        return imageString;
    }
}
