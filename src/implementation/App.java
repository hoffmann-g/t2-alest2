package implementation;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class App {
    
    public static void main(String[] args){

        String path = "teste_slide.txt";

        List<Box> catalog = getCatalog(path);

        BoxDigraph graph = new BoxDigraph(catalog, catalog.size());

        HashMap<Box, Integer> distances = graph.getLongestPathsFrom(new Box("680 579 148"));

        writeToDot(graph.toDot());
        System.out.println("\n# dotfile exported");

        System.out.println("\n// ADJ LIST");
        for (LinkedList<Box> ll : graph.getAdj()){
            System.out.println(ll);
        }

        System.out.println("\nVertices: " + graph.V() + ", Edges: " + graph.E());

        for (Map.Entry<Box, Integer> entry : distances.entrySet()) {
            Box key = entry.getKey();
            Integer value = entry.getValue();

            if (value == Integer.MIN_VALUE) {
                System.out.println(key.toString() + " = not reachable");
            } else {
                System.out.println(key.toString() + " = " + value);
            }
        }
        System.out.println("\nLongest path size: " + graph.getLongestPathSize());

        

    }

    public static List<Box> getCatalog(String path){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

            List<Box> catalogo = new ArrayList<>();

            String line = bufferedReader.readLine();
            while (line != null){
                catalogo.add(new Box(line));
            
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            return catalogo;

        } catch (Exception e){
            System.out.println("could not read specified path");
            return null;
        } 
    }

    public static void writeToDot(String txt){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.dot"));
            bufferedWriter.write(txt);
            bufferedWriter.close();
        } catch (IOException e){
            System.out.println("no puedo printarle lo caralho");
        }
    }
}
