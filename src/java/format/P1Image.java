package format;


import java.util.Optional;

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
        StringBuilder imageString = new StringBuilder(this.getSubtypeName() + "\n");
        for (String comment : this.comment) {
            imageString.append(comment).append("\n");
        }
        imageString.append(this.width).append(" ").append(this.height).append("\n");
        int counter = 0;
        for (Integer bodyValue : this.bodyValues) {
            imageString.append(bodyValue);
            counter++;
            Optional.of(counter)
                    .filter(c -> c % this.width == 0)
                    .ifPresent(c -> imageString.append("\n"));

            Optional.of(counter)
                    .filter(c -> c % this.width != 0)
                    .ifPresent(c -> imageString.append(" "));

        }
        return imageString.toString();
    }
}
