import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CaixasAlgoritmo {
    
    public static void main(String[] args) {

        // String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = "C:\\Users\\endrew.santos\\Downloads\\caixastuff\\teste1000.txt";
        Path path = Paths.get(nameComplete);
        Map<String, Caixa> vertices = new HashMap<>();
        Map<String, Integer> map = new HashMap<>();
        Integer intRelacao = 1;
        Graph g = new Graph(120000);
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
            String linha = "";

            while (sc.hasNext()) {
                linha = sc.nextLine();
                if (!map.keySet().contains(linha)) {
                    Caixa putCaixa = new Caixa(linha);
                    vertices.put(linha, putCaixa);
                    map.put(linha, intRelacao);
                    intRelacao++;
                }
                
            }

            Scanner aleatorioSC = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8));

            while (aleatorioSC.hasNext()) {
                linha = aleatorioSC.nextLine();
                Caixa cx1 = vertices.get(linha);
                for(String key: map.keySet()){
                        if(!key.equals(linha)){
                        Caixa cx2 = vertices.get(key);
                        //System.out.println(key);
                        try{
                            int compare = cx1.compareTo(cx2);
                            if(compare < 0){ //if caixa1 < caixa2 then caixa 1 cabe dentro de caixa2
                                g.addEdge(map.get(linha), map.get(key));
                                //System.out.println(" "+linha + "->" + key);
                            }
                        }catch(NullPointerException npe){
                            System.out.println(linha + " não encontrado");
                        }
                    }
                }
            }
            sc.close();

            CaminhamentoEmProfundidade dfs = new CaminhamentoEmProfundidade(g, 100);
            boolean hasPath = dfs.hasPath(100);
            System.out.println("HasPath to 100? " + hasPath);
            String[] argumentos = new String[1];
            argumentos[0] = "C:\\Users\\endrew.santos\\Downloads\\caixastuff\\teste1000.txt";
            //CaminhamentoEmProfundidade.main(argumentos);
            //System.out.println("O nro de vertices é: " + vertices.size());
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
            System.exit(0);
        }
    }
}
