package processing.validation;

public interface ICharCompareCallback {
    void success(String content);

    void fail(String content);
}
