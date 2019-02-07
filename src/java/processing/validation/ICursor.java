package processing.validation;

public interface ICursor {
    void nextChar();
    void nextValidator();
    void nextValidatorList();
    void performChanges();
    void fillValidationList();

}
