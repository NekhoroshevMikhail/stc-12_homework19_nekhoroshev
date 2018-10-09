package Model;

import Model.Exceptions.IncorrectSentenceEndException;
import Model.Exceptions.NullValueException;

import java.util.Objects;

public class Sentence {
    private String originalSentense;
    private String noPunctuationString;

    private String removePunctuationRegexString = "[.,!?\\-()]";
    public Sentence(String originalSentense) throws NullValueException, IncorrectSentenceEndException {
        if (originalSentense == null) {
            throw new NullValueException("sentence");
        }

        if (!SentenceEndingChars.isEndOfSentence((originalSentense.charAt(originalSentense.length()-1)))) {
            throw new IncorrectSentenceEndException();
        }

        this.originalSentense = originalSentense;
        this.noPunctuationString = this.originalSentense.replaceAll(removePunctuationRegexString, "");
    }

    public String getOriginalSentenseText() {
        return originalSentense;
    }

    public boolean containsWord(String word) {
        return noPunctuationString.contains(word);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return Objects.equals(originalSentense, sentence.originalSentense) &&
                Objects.equals(noPunctuationString, sentence.noPunctuationString);

    }

    @Override
    public int hashCode() {
        return Objects.hash(originalSentense, noPunctuationString);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Sentence{");
        builder.append(originalSentense);
        builder.append(" }");
        return builder.toString();
    }
}
