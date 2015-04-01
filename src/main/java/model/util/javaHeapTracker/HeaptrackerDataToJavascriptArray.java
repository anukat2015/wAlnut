package model.util.javaHeapTracker;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author Q Liu (quinnliu@vt.edu)
 * @date 4/1/2015
 */
public class HeaptrackerDataToJavascriptArray {
    public static void main(String []av) throws Exception {
        HeaptrackerDataToJavascriptArray
                .printToConsole("./src/main/java/model/util/javaHeapTracker/heaptrace.csv");
    }

    /**
     * The purpose is to print to console the contents of the heaptrace.csv file
     * into a Javascript array of the following format:
     *
     * Example input heaptrace.csv:
     *
     * 0.190750, 229960
     * 0.194708, 429976
     *
     * The first column is seconds and the second column is MB used by heap.
     *
     * The output will look like the following with the seconds column multiplied
     * by 1000 to represent milliseconds.
     *
     * var dataset = [
     *              [190.75, 229960], [194.71, 429976]
    *                ];
     *
     * @param CSV_filename
     * @throws IOException
     */
    public static void printToConsole(String CSV_filename) throws IOException {
        DecimalFormat df1 = new DecimalFormat("#.00");
        File CSV_file = new File(CSV_filename);
        List<String> linesWithCommas = Files.readAllLines(CSV_file.toPath(),
                StandardCharsets.UTF_8);

        String javascriptArray = "var dataset = [\n\t\t\t";

        int numberOfValuesEachLine = 3;
        int currentElement = 1;
        for (String line : linesWithCommas) {
            String[] rowValues = line.split(",");

            double rowValueInSeconds = Double.valueOf(rowValues[0]);
            double rowValueInMilliseconds = rowValueInSeconds * 1000;

            javascriptArray += "[" + df1.format(rowValueInMilliseconds) + "," + rowValues[1] + "]";

            if (currentElement < linesWithCommas.size()) {
                javascriptArray += ", ";
                currentElement++;
            } // else don't add comma

            if (numberOfValuesEachLine == 1) {
                javascriptArray += "\n\t\t\t";
                numberOfValuesEachLine = 3;
            } else {
                numberOfValuesEachLine--;
            }
        }

        javascriptArray += "\n\t\t\t  ];";
        System.out.print(javascriptArray);
    }
}
