package Model.Exceptions;

public class IncorrectSentenceEndException extends Exception {
    private static final String internalMessage = "This sentence has incorrect end character";

    public IncorrectSentenceEndException() {
        super(internalMessage);
    }
}
