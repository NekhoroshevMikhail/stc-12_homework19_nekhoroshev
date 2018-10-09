package Model;

import java.util.ArrayList;
import java.util.List;

public class SentenceEndingChars {

    private static List<Character> sentenceEndChars;

    static {
        sentenceEndChars = new ArrayList<>();
        sentenceEndChars.add('.');
        sentenceEndChars.add('?');
        sentenceEndChars.add('!');
    }

    public static boolean isEndOfSentence(Character character) {
        return sentenceEndChars.contains(character);
    }

}
