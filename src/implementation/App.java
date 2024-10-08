package implementation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {

        String path = "casos/caso10000.txt";

        List<Box> catalog = getCatalog(path);

        BoxDigraph graph = new BoxDigraph(catalog);

        // System.out.println("# calculating longest path");
        System.out.println("\n# Longest path size found: " + graph.getLongestPathSize());

        System.out.println("(Graph is composed by " + graph.getV() + " vertices and " + graph.getE() + " edges.)");

        // writeToDot(graph.toDot());
        // System.out.println("\n# dotfile exported");

        //runPythonScript("dotrender.py");

    }

    public static List<Box> getCatalog(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

            List<Box> catalogo = new ArrayList<>();

            String line = bufferedReader.readLine();

            while (line != null) {
                catalogo.add(new Box(line));

                line = bufferedReader.readLine();
            }

            bufferedReader.close();

            return catalogo;

        } catch (Exception e) {
            return null;
        }
    }

    public static void writeToDot(String txt) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.dot"));
            bufferedWriter.write(txt);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("no puedo printarle lo caralho");
        }
    }

    public static void runPythonScript(String pythonScript) {
        try {
            // Command to execute Python script (adjust as necessary)
            String pythonScriptPath = "python3";

            // Build the command
            ProcessBuilder processBuilder = new ProcessBuilder(pythonScriptPath, pythonScript);
            processBuilder.redirectErrorStream(true); // Redirect error stream to output stream

            // Start the process
            Process process = processBuilder.start();

            // Read output from Python script
            java.io.InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            System.out.print("\npython log: ");

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to complete and get the exit code
            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            System.err.println("Error executing Python script: " + e.getMessage());
        }
    }
}
