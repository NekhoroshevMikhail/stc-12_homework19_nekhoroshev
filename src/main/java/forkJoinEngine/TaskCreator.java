package forkJoinEngine;

import Model.Sentence;
import interfaces.ITask;
import interfaces.ITaskCreator;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskCreator implements ITaskCreator {
    public ITask createTask(String[] sources, int start, int end, ConcurrentLinkedQueue<Sentence> sentenceForSearchQueue) {
        return new FillParsedSentenceQueueTask(sources, start, end, sentenceForSearchQueue, this);
    }
}
