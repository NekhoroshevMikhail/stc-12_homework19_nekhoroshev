package interfaces;

import Model.Sentence;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface ITaskCreator {
    ITask createTask(String[] sources, int start, int end, ConcurrentLinkedQueue<Sentence> sentenceForSearchQueue);
}
