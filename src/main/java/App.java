
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;

public class App {
    public static void main(String[] args) {
        // File file = new File();
        handleFile("README.txt");
    }

    public static void handleFile(String filename) {

        try (FileReader reader = new FileReader(filename);
             BufferedReader buffer = new BufferedReader(reader)) {

            for (String line = buffer.readLine(); line != null; line = buffer.readLine()) {
                String[] words = line.split(" ");
                switch (words[0]) {
                    case "CREATE":
                        create(words[1]);
                        break;
                    case "APPEND":
                        appendWords(words);
                        break;
                    case "DELETE":
                        delete(words[1]);
                        break;
                    case "COPY":
                        copy(words[1], words[2]);
                        break;
                    default :
                        continue;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static boolean create(String path) {
        File file = new File(path);
        try {
            if (file.createNewFile()) {
                return true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean appendWords(String[] words) {
        try (FileWriter writer = new FileWriter(words[1], true);
            PrintWriter printer = new PrintWriter(writer)) {
                for (int i = 2; i < words.length; i++) {
                    String space = (i==words.length-1) ? "%n" : " ";
                    printer.printf(words[i] + space);
                }
                return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean delete(String path) {
        File file = new File(path);
        return file.delete();
    }

    public static boolean copy(String source, String target) {
        String[] targets = target.split("/");
        try {
            for(int i = 0; i < targets.length - 1; i++) {
                File folder = new File(pathBuilder(targets, i));
                folder.mkdir();
            }
            File file = new File(target);
            appendLines(source, target);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static String pathBuilder(String[] targets, int end) {
        StringBuilder target = new StringBuilder();
        for(int i = 0; i <= end; i++) {
            target.append(targets[i]);
        }
        return target.toString();
    }

    public static boolean appendLines(String source, String target) {
        try(FileReader fileReader = new FileReader(source);
            BufferedReader reader = new BufferedReader(fileReader);
            FileWriter fileWriter = new FileWriter(target, true);
            PrintWriter writer = new PrintWriter(fileWriter)) {

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                writer.println(line);
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}






















































