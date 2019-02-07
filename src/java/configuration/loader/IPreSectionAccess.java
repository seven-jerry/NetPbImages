package configuration.loader;

import processing.validation.TargetKey;
import processing.datatype.CharType;
import processing.procedure.AbstractProcedure;

public interface IPreSectionAccess {
    void putTargetKey(String docId, TargetKey targetKey);
    void putVariable(String docId, CharType charType);
    void putProcedure(String docId, AbstractProcedure procedure);
}
