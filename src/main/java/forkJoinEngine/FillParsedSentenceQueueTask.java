package forkJoinEngine;

import Model.Sentence;
import implementation.SourceReader;
import interfaces.ITask;
import interfaces.ITaskCreator;
import parser.SourceSentenceIterator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.RecursiveAction;

public class FillParsedSentenceQueueTask extends RecursiveAction implements ITask {

    private static final Integer THRESHOLD = 200;

    private ConcurrentLinkedQueue<Sentence> sentenceForSearchQueue;
    private String[] sources;
    private Integer start;
    private Integer end;
    private ITaskCreator taskCreator;

    public FillParsedSentenceQueueTask(String[] sources, Integer start, Integer end, ConcurrentLinkedQueue<Sentence> sentenceForSearchQueue, ITaskCreator taskCreator) {
        this.sentenceForSearchQueue = sentenceForSearchQueue;
        this.sources = sources;
        this.start = start;
        this.end = end;
        this.taskCreator = taskCreator;
    }

    @Override
    protected void compute() {
        if (end - start > THRESHOLD) {
            int middle = (end + start) / 2;

            ITask firstSubTask = taskCreator.createTask(sources, start, middle, sentenceForSearchQueue);
            ITask secondSubTask = taskCreator.createTask(sources, middle + 1, end, sentenceForSearchQueue);

            secondSubTask.forkTask();
            firstSubTask.forkTask();

            secondSubTask.joinTask();
            firstSubTask.joinTask();

        } else {
            for (int i = start; i < end; i++) {
                try {
                    URL sourceUrl = new URL(sources[i]);
                    SourceReader sourceReader = new SourceReader(new InputStreamReader(sourceUrl.openStream()));
                    SourceSentenceIterator iterator = new SourceSentenceIterator(sourceReader);
                    Sentence sentence;
                    while((sentence = iterator.getNextSentence()) != null) {
                        sentenceForSearchQueue.add(sentence);
                    }
                    iterator.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean isTaskDone() {
        return isDone();
    }

    @Override
    public void forkTask() {
        this.fork();
    }

    @Override
    public void joinTask() {
        this.join();
    }
}
