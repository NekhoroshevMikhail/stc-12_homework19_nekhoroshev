package forkJoinEngine;

import Model.Sentence;
import interfaces.ITask;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SearchWordsInSentenseThread extends Thread {

    private ConcurrentLinkedQueue<String> resultList;
    private ConcurrentLinkedQueue<Sentence> sentencesForSearchQueue;
    private String[] words;
    private ITask referenceTask;

    public SearchWordsInSentenseThread(
            ConcurrentLinkedQueue<Sentence> sentencesForSearchQueue,
            String[] words,
            ConcurrentLinkedQueue<String> resultList,
            ITask referenceTask) {
        this.sentencesForSearchQueue = sentencesForSearchQueue;
        this.resultList =resultList;
        this.words = words;
        this.referenceTask = referenceTask;
    }

    @Override
    public void run() {

        System.out.println("start search of sentences");
        while (!referenceTask.isTaskDone()) {
            Sentence sentence = sentencesForSearchQueue.poll();
            if (sentence != null) {
                for (String word : words) {
                    if (sentence.containsWord(word)) {
                        resultList.add(sentence.getOriginalSentenseText());
                        break;
                    }
                }
            }
        }
        System.out.println("FINISH search of sentences");
    }
}