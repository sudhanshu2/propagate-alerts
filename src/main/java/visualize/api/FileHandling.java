package visualize.api;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Random;

public class FileHandling {
    private final Timestamp timestamp;

    private FileWriter fileWriter;
    private File file;


    public FileHandling(String fileName) {
        try {
            file = new File(fileName);

            if (file.createNewFile()) {
                System.out.println("created new file\n");
            }

            fileWriter = new FileWriter(file, true);
        } catch (Exception e) {
            System.out.println("could not load file\n");
            System.exit(-1);
        }

        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public void addToCSV(boolean addTimeStamp, String... dataArr) {
        timestamp.setTime(System.currentTimeMillis());

        try {
            if (addTimeStamp) {
                fileWriter.append(timestamp.toString());
            }

            fileWriter.append(",");

            for (int i = 0; i < dataArr.length - 1; i++) {
                fileWriter.append(dataArr[i]);
                fileWriter.append(",");
            }

            fileWriter.append(dataArr[dataArr.length - 1]);
            fileWriter.append("\n");

        } catch (Exception e) {
            System.out.println("could not write to file\n");
            System.exit(-1);
        }
    }

    public void closeCSV() {
        try {
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("could not close file\n");
            System.exit(-1);
        }
    }

    public void wipeCSV() {
        PrintWriter printWriter = new PrintWriter(fileWriter, false);
        printWriter.flush();
    }

    protected static void createNodeCSV(int count, int lowerBound, int higherBound) {
        try {
            File file = new File("csv/nodes.csv");
            FileWriter fileWriter = new FileWriter(file, false);
            Random random = new Random();
            final int UPPERBOUND = 100;
            int trigger = new Random().nextInt(count - 1);

            for (int i = 0; i < count - 1; i++) {
                fileWriter.append(Integer.toString(random.nextInt(UPPERBOUND)));
                fileWriter.append(",");
                fileWriter.append(Integer.toString(random.nextInt(UPPERBOUND)));
                fileWriter.append(",");
                fileWriter.append(Integer.toString(random.nextInt(lowerBound)));
                fileWriter.append(",");
                if (i == trigger) {
                    fileWriter.append(Integer.toString(2 * (lowerBound + random.nextInt(higherBound - lowerBound))));
                } else {
                    fileWriter.append(Integer.toString(lowerBound + random.nextInt(higherBound - lowerBound)));
                }

                fileWriter.append("\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("could not create nodes file\n");
            System.exit(-1);
        }

    }

    public void deleteCSV() {
        if (file.delete()) {
            System.out.println("deleted file successfully\n");
        } else {
            System.out.println("could not delete file\n");
            System.exit(-1);
        }
    }
}
