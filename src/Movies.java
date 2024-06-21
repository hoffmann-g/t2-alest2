import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Movies {
    public static void main(String[] args) {

        // String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = "E:\\vs-workspace\\vs-workspace-java\\BasicGraphs\\movies.txt";
        Path path = Paths.get(nameComplete);
        Set<String> vertices = new HashSet<>();
        Map<String, Integer> map = new HashMap<>();
        Integer intRelacao = 1;
        Graph g = new Graph(120000);
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
            String linha = "";

            while (sc.hasNext()) {
                linha = sc.nextLine();
                String[] linhaDiv = linha.split("/");
                boolean first = true;
                String nomeFilme = linhaDiv[0].toLowerCase();
                for (String e : linhaDiv) {
                    String nameFormated = e.toLowerCase();
                    if (!first) {
                        String[] names = nameFormated.split(",");
                        if (names.length > 1) {
                            String lastName = names[0];
                            String firstName = names[1].trim().split(" ")[0];
                            nameFormated = firstName + " " + lastName;
                        }
                    } else {
                        nameFormated = nomeFilme;
                    }
                    first = false;

                    if (!map.keySet().contains(nameFormated)) {
                        map.put(nameFormated, intRelacao);
                        intRelacao++;
                    }
                }

            }
            int n = 0;
            for (String s : map.keySet()) {
                System.out.println(n++ + " " + s + " " + map.get(s));
                if (n > 100)
                    break;
            }

            System.out.println("First actor: " + map.get("Bryan Brown"));

            Scanner aleatorioSC = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8));
            boolean first = true;
            while (aleatorioSC.hasNext()) {
                linha = aleatorioSC.nextLine();
                String[] linhaDiv = linha.split("/");
                String nomeFilme = linhaDiv[0].toLowerCase();
                first = true;
                String nameFormated ;
                for (int i = 1; i < linhaDiv.length; i++) {
                    nameFormated = linhaDiv[i].toLowerCase();
                    String[] names = nameFormated.split(",");
                    if (names.length > 1) {
                        String lastName = names[0];
                        String firstName = names[1].trim().split(" ")[0];
                        nameFormated = firstName + " " + lastName;
                    }
                    if(i%2000==0)
                    //System.out.println("Filme: " + map.get(nomeFilme) + ", Actor name: " + map.get(nameFormated));
                    g.addEdge(map.get(nomeFilme), map.get(nameFormated));
                }
            }
            sc.close();
            Scanner in = new Scanner(System.in);
            boolean found = true;
            Integer autor1;
            Integer autor2;
            do {
                System.out.println("Autor 1 nome: ");
                String autor1S = in.nextLine();
                autor1 = map.get(autor1S);
                found = autor1 != null;
            } while (!found);
            do {
                System.out.println("Autor 2 nome: ");
                String searched = in.nextLine();
                autor2 = map.get(searched);
                found = autor2 != null;
            } while (!found);

            CaminhamentoEmLargura dfs = new CaminhamentoEmLargura(g, autor1);
            boolean hasPath = dfs.hasPath(autor2);
            String hasPathMessage = hasPath ? "Yes, from actor 1 you can get to actor 2" : "nope" ;
            System.out.println(hasPathMessage);

            Iterable<Integer> pathTo = dfs.pathTo(autor2);
            for(Integer i: pathTo){
                System.out.println(map.get(i) + ", ");
            }

            dfs.main(args);
            System.out.println("O nro de vertices é: " + vertices.size());
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
            System.exit(0);
        }
    }
}