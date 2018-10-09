package parser;

import Model.Exceptions.IncorrectSentenceEndException;
import Model.Exceptions.NullValueException;
import Model.Sentence;
import Model.SentenceEndingChars;
import interfaces.ISourceReader;

import java.io.IOException;

public class SourceSentenceIterator {

    private ISourceReader reader;

    public SourceSentenceIterator(ISourceReader reader) throws IOException {
        this.reader = reader;
    }

    public void close(){
        if (reader != null) {
            try {
                reader.close();
                reader = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Sentence getNextSentence() {
        StringBuilder wholeDataBuilder = new StringBuilder();
        int currentCharacterCode;
        try {
            currentCharacterCode = reader.read();
            while (currentCharacterCode != -1) {
                if ((char)currentCharacterCode != '\r' && (char)currentCharacterCode != '\n') {
                    wholeDataBuilder.append((char)currentCharacterCode);
                }
                int nextCharacterCode = reader.read();

                if(SentenceEndingChars.isEndOfSentence((char)currentCharacterCode)) {
                    while(SentenceEndingChars.isEndOfSentence((char)nextCharacterCode)) {
                        nextCharacterCode = reader.read();
                    }
                    break;
                }
                currentCharacterCode = nextCharacterCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (wholeDataBuilder.length() == 0) {
            return null;
        }

        Sentence sentence = null;
        try {
            sentence = new Sentence(wholeDataBuilder.toString());

        } catch (NullValueException | IncorrectSentenceEndException e) {
            e.printStackTrace();
        }
        return sentence;
    }
}
