package implementation;

import Model.Sentence;
import forkJoinEngine.FillParsedSentenceQueueTask;
import forkJoinEngine.SaveFoundSentensesThread;
import forkJoinEngine.SearchWordsInSentenseThread;
import forkJoinEngine.TaskCreator;
import interfaces.IOccurenciesGetter;
import interfaces.ITaskCreator;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;

public class OccurenciesGetter implements IOccurenciesGetter {
    @Override
    public void getOccurencies(String[] sources, String[] words, String res) {
        ForkJoinPool pool = new ForkJoinPool();
        ConcurrentLinkedQueue<Sentence> sentencesForSearchQueue = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<String> foundSentences = new ConcurrentLinkedQueue<>();
        ITaskCreator creator = new TaskCreator();
        FillParsedSentenceQueueTask fillSentenseQueueTask = new FillParsedSentenceQueueTask(sources, 0, sources.length, sentencesForSearchQueue, creator);
        SearchWordsInSentenseThread searchWordsInSentenseThread = new SearchWordsInSentenseThread(sentencesForSearchQueue, words, foundSentences, fillSentenseQueueTask);
        searchWordsInSentenseThread.setDaemon(true);
        SaveFoundSentensesThread savingThread = new SaveFoundSentensesThread(res, foundSentences, fillSentenseQueueTask);
        savingThread.setDaemon(true);
        pool.execute(fillSentenseQueueTask);
        searchWordsInSentenseThread.start();
        savingThread.start();
        fillSentenseQueueTask.join();
        try {
            searchWordsInSentenseThread.join();
            savingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
