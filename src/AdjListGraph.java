import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AdjListGraph implements Iterable<LinkedList<Caixa>>{

    public List<LinkedList<Caixa>> adjList;

    public AdjListGraph() {
        adjList = new ArrayList<>(); 
    }

    public AdjListGraph(List<Caixa> catalogo) {
        adjList = new ArrayList<>(mapBySize(catalogo));
    }

    public List<LinkedList<Caixa>> getAdjList() {
        return adjList;
    }

    private List<LinkedList<Caixa>> mapBySize(Iterable<Caixa> catalog){
        List<LinkedList<Caixa>> adjList = new ArrayList<>();

        // iterating over every of the given catalog 
        for (Caixa c : catalog){
            LinkedList<Caixa> linkedList = new LinkedList<>();

            linkedList.add(c);
            adjList.add(linkedList);
        }

        // adding the edges to the adj list
        for (Caixa c : catalog){
            for (LinkedList<Caixa> ll : adjList){
                // if c > ll[0]
                if (c.compareTo(ll.getFirst()) > 0){
                    ll.add(c);
                }
            }
        }

        return adjList;
    }

    @Override
    public Iterator<LinkedList<Caixa>> iterator() {

        return new Iterator<LinkedList<Caixa>>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < adjList.size();
            }

            @Override
            public LinkedList<Caixa> next() {
                return adjList.get(currentIndex++);
            }
            
        };
    }
    
}
