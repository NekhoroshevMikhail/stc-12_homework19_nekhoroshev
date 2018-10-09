package Model.Exceptions;

public class NullValueException extends Exception{

    private static final String internalMessage = "This value can not be null";

    /**
     * Constructor
     * @param valueName - name of value, which is null
     */
    public NullValueException(String valueName) {
        super(internalMessage + " " + valueName);
    }
}
