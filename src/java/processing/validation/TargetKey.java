package processing.validation;

public enum TargetKey {
    COMMENT("comment"),
    SUBTYPE("subtype"),
    IMAGE_WIDTH("imageWidth"),
    IMAGE_HEIGHT("imageHeight"),
    BODY_VALUE("bodyValue"),
    MAX_COLOR_VALUE("maxColorValue");
    String stringKey;
    public static TargetKey getEnum(String key){
        for(TargetKey targetKey: TargetKey.values()){
            if(targetKey.stringKey.equals(key) == true){
                return targetKey;
            }
        }
        throw new NullPointerException();
    }
    TargetKey(String stringKey){
        this.stringKey = stringKey;
    }
    public String getValue(){
        return this.stringKey;
    }
}
