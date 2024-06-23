import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AdjacencyListMapping {
    
    public static void main(String[] args){

        String path = "teste20.txt";

        System.out.println("\nCurrently reading: " + path);

        System.out.println("\n// TXT FILE:");
        getCatalogo(path).forEach(System.out::println);;

        System.out.println("\n// ADJ LIST:");
        AdjListGraph adjListGraph = new AdjListGraph(getCatalogo(path));
        adjListGraph.forEach(System.out::println);

        System.out.println("\nfinished");

    }

    public static List<Caixa> getCatalogo(String path){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

            List<Caixa> catalogo = new ArrayList<>();

            String line = bufferedReader.readLine();
            while (line != null){
                catalogo.add(new Caixa(line));
            
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            return catalogo;

        } catch (Exception e){
            System.out.println("could not read specified path");
            return null;
        } 
    }
}
