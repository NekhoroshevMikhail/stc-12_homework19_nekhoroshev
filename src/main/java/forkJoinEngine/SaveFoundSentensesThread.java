package forkJoinEngine;

import interfaces.ITask;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SaveFoundSentensesThread extends Thread {

    private ConcurrentLinkedQueue<String> resultSentenses;
    private String pathToResultFile;
    private ITask referenceTask;

    public SaveFoundSentensesThread(String pathToResultFile,
                                    ConcurrentLinkedQueue<String> resultSentenses,
                                    ITask referenceTask) {
        this.resultSentenses = resultSentenses;
        this.pathToResultFile = pathToResultFile;
        this.referenceTask = referenceTask;
    }

    private void clearResultFile(String resultFile) {
        try (FileOutputStream fos = new FileOutputStream(resultFile)) {
            fos.write(new byte[]{});
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        clearResultFile(pathToResultFile);

        FileWriter writer;
        try {
            writer = new FileWriter(pathToResultFile, true);
            System.out.println("start save incoming sentences");
            while (!referenceTask.isTaskDone()) {
                String sentenceText = resultSentenses.poll();
                if(sentenceText != null) {
                    writer.write(sentenceText);
                }
            }
            System.out.println("FINISH save incoming sentences");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
