package processing.validation;

import java.util.Optional;
import java.util.stream.Stream;

public enum TargetKey {
    COMMENT("comment"),
    SUBTYPE("subtype"),
    IMAGE_WIDTH("imageWidth"),
    IMAGE_HEIGHT("imageHeight"),
    BODY_VALUE("bodyValue"),
    MAX_COLOR_VALUE("maxColorValue");
    String stringKey;
    public static TargetKey getEnum(String key){

        Optional<TargetKey> targetKey = Stream.of(TargetKey.values())
                .filter(t -> t.stringKey.equals(key))
                .findAny();

        return targetKey.orElseThrow(NullPointerException::new);
    }
    TargetKey(String stringKey){
        this.stringKey = stringKey;
    }
    public String getValue(){
        return this.stringKey;
    }
}
