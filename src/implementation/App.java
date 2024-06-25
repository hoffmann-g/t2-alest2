package implementation;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class App {
    
    public static void main(String[] args){

        String path = "teste100.txt";

        List<Box> catalog = getCatalog(path);

        BoxDigraph graph = new BoxDigraph(catalog, catalog.size());

        System.out.println("\n// ADJ LIST");

        for (LinkedList<Box> ll : graph.getAdj()){
            System.out.println(ll);
        }

        System.out.println("\nVertices: " + graph.V() + ", Edges: " + graph.E());

        writeToDot(graph.toDot());

        System.out.println("\n// dotfile exported");

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
