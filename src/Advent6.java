import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Advent6
{
    public static void main(String[] args)
    {
        String[][] file;
        long[][] first = null;
        long[][] second = new long[2][1];

        try {
            file = convertFileToArrayOfArrays("src/time.txt");
            first = initTable(file);
            second = raceData(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Part 1 - Total solutions: " + solutions(first));
        System.out.println("Part 2 - Total solutions: " + solutions(second));

    }

    private static int solutions(long[][] data){
        int solutions = 0;
        int total = 1;

        for(int raceInd = 0; raceInd < data[0].length; raceInd++){
            solutions = 0;
            for(long curVel = 0; curVel <= data[0][raceInd]; curVel++){
                long space = curVel * (data[0][raceInd] - curVel);
                if(space > data[1][raceInd]){
                    solutions++;
                }
                //System.out.println("N: " + raceInd + " Vel: " + curVel + " Space: " + space);

            }
            total *= solutions;
            //System.out.println(" Sol: " + solutions);
            //System.out.println(" Tot: " + total + "\n");
        }

        return total;

    }

    private static long[][] raceData(String[][] file){
        String time = "";
        String dist = "";
        for(int i = 1; i < file[0].length; i++){
            time += file[0][i];
            dist += file[1][i];
        }

        long finTime = Long.parseLong(time);
        long finDist = Long.parseLong(dist);

        long[][] result = {{finTime}, {finDist}};


        return result;

    }

    private static String[][] convertFileToArrayOfArrays(String filePath) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] lines = text.split("\n");
        String[][] arrayOfArrays = new String[lines.length][];

        for (int i = 0; i < lines.length; i++) {
            arrayOfArrays[i] = lines[i].trim().split("\\s+");
        }

        return arrayOfArrays;
    }

    private static long[][] initTable(String[][] table) {
        int line = -1;
        int limit = table.length;

        List<List<Long>> tempList = new ArrayList<>();

        // Find the start line of the relevant data
        for (int k = 0; k < table[0].length; k++) {
            if (table[k][0].contains("Time:")) {
                line = k;
                break;
            }
            if (line != -1){
                limit = line + 2;
                break;
            }
        }

        // Populate the tempList with data from the table
        for (int i = 0; i < limit - line; i++) {
            List<Long> row = new ArrayList<>();
            for (int k = 1; k < table[line + i].length; k++) {
                row.add(Long.parseLong(table[line + i][k]));
            }
            tempList.add(row);
        }

        // Convert tempList to long[][]
        long[][] storage = new long[tempList.size()][tempList.get(0).size()];
        for(int i = 0; i < tempList.size(); i++) {
            for(int k = 0; k < tempList.get(i).size(); k++){
                storage[i][k] = tempList.get(i).get(k);
            }
        }

        return storage;
    }
}