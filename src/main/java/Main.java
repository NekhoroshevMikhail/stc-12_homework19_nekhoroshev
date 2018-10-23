import implementation.OccurenciesGetter;
import interfaces.IOccurenciesGetter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Main {
    private static final String pathToFolder = "M:\\Source\\Inno\\LongTermWork1\\testSet\\";
    private static final String pathToResult = "M:\\Source\\Inno\\LongTermWork1\\result.txt";

    public static String[] getFIleNames() {
        File folder = new File(pathToFolder);
        File[] listOfFiles = folder.listFiles();
        List<String> results = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                results.add("file:" + pathToFolder +  listOfFiles[i].getName());
            }
        }
        return results.toArray(new String[0]);
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        IOccurenciesGetter getter = new OccurenciesGetter();
        getter.getOccurencies(getFIleNames(),new String[] {"starter", "smarter"}, pathToResult);
        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("total time = " + TimeUnit.MILLISECONDS.toSeconds(elapsed));
    }
}
