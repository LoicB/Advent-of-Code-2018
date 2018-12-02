import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileToArray {

    public static String[] readFile(String file) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                lines.add(currentLine);
                currentLine = reader.readLine();
            }
        } catch (IOException ex) {
            System.err.println("Error whislt reading the file: " + ex.getLocalizedMessage());
        }

        String[] linesArr = new String[lines.size()];
        linesArr = lines.toArray(linesArr);
        return linesArr;
    }
}