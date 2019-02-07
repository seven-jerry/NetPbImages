package processing.validation;

import processing.validation.ICharCompareCallback;

public interface ICharValidator {
    void charInRange(String needle, String docId, String range, ICharCompareCallback callback);
}
