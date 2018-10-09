package forkJoinEngine;

import Model.Exceptions.IncorrectSentenceEndException;
import Model.Exceptions.NullValueException;
import Model.Sentence;
import interfaces.ITask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class SearchWordsInSentenseThreadTest {
    private SearchWordsInSentenseThread searchThread;
    private ConcurrentLinkedQueue<Sentence> sentences;
    private ConcurrentLinkedQueue<String> resultList;
    private ITask task;

    @BeforeEach
    void init() {
        sentences = new ConcurrentLinkedQueue<>();
        resultList = new ConcurrentLinkedQueue<>();
    }

    @Test
    void runTest() throws IncorrectSentenceEndException, NullValueException {

        String words[] = new String[]{"asd"};
        task = Mockito.mock(ITask.class);

        Sentence firstSentence = new Sentence("qasd asd dsds fff gggg hklkypom.");
        Sentence secondSentence = new Sentence("mpioremtlk hklkypom.");
        sentences.add(firstSentence);
        sentences.add(secondSentence);

        when(task.isTaskDone()).thenReturn(false, false, true);
        searchThread = new SearchWordsInSentenseThread(
                sentences,
                words,
                resultList,
                task);

        searchThread.start();
        try {
            searchThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int queueSize = resultList.size();
        String originalSentenceText = resultList.poll();
        assertTrue(queueSize == 1 && originalSentenceText.equals(firstSentence.getOriginalSentenseText()));
    }
}