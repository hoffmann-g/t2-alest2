package implementation;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class BoxDigraph {
	private static final String NEWLINE = System.getProperty("line.separator");

	private final int V; // number of vertices in this digraph
	private int E; // number of edges in this digraph
	private List<LinkedList<Box>> adj; // adj[v] = adjacency list for vertex v

	public BoxDigraph(int V) {
		if (V < 0)
			throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");

		this.V = V;
		this.E = 0;
		adj = new ArrayList<LinkedList<Box>>();
	}

	public BoxDigraph(Iterable<Box> catalog, int V) {
		if (V < 0)
			throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");

		this.V = V;
		this.E = 0;
		adj = new ArrayList<LinkedList<Box>>();

		for(Box b1 : catalog){
			for(Box b2 : catalog){
				if(validateBox(b1, b2)){
					addEdge(b1, b2);
					E++;
				} else if (b1.equals(b2)){
					addVertex(b1);
				}
			}
		}
	}

	/* 
	@SuppressWarnings("unchecked")
	public GraphDigraph(In in) {
		try {
			this.V = in.readInt();
			if (V < 0)
				throw new IllegalArgumentException("number of vertices in a Digraph must be nonnegative");
			indegree = new int[V];
			adj = (HashSet<Box>[]) new HashSet[V];
			for (int v = 0; v < V; v++) {
				adj[v] = new HashSet<Box>();
			}
			int E = in.readInt();
			if (E < 0)
				throw new IllegalArgumentException("number of edges in a Digraph must be nonnegative");
			for (int i = 0; i < E; i++) {
				int v = in.readInt();
				int w = in.readInt();
				addEdge(v, w);
			}
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("invalid input format in Digraph constructor", e);
		}
	}
	*/

	public int V() {
		return V;
	}

	public int E() {
		return E;
	}

	public List<LinkedList<Box>> getAdj() {
		return adj;
	}

	private boolean validateBox(Box box1, Box box2) {
		return (box1.compareTo(box2) < 0);
	}

	public void addVertex(Box b1){
		for (LinkedList<Box> ll : adj){
			if (ll.getFirst().equals(b1)){
				return;
			}
		}

		LinkedList<Box> newAdj = new LinkedList<Box>();
		newAdj.add(b1);

		adj.add(newAdj);
	}

	public void addEdge(Box b1, Box b2) {
		if(validateBox(b1, b2)){
			for (LinkedList<Box> ll : adj){
				if (ll.getFirst().equals(b1)){
					ll.add(b2);
					return;
				}
			}
	
			LinkedList<Box> newAdj = new LinkedList<Box>();
			newAdj.add(b1);
			newAdj.add(b2);
	
			adj.add(newAdj);
		}

		// throw new IllegalArgumentException("cannot add an edge from a bigger box to a smaller one");		
	}

	public LinkedList<Box> getAdjacencies(Box box) {
		for (LinkedList<Box> ll : adj){
			if (ll.getFirst().equals(box)){
				return ll;
			}
		}
		throw new NoSuchElementException("box not found");
	}

	public int outdegree(Box box) {
		for (LinkedList<Box> ll : adj){
			if (ll.getFirst().equals(box)){
				return ll.size() - 1;
			}
		}
		throw new NoSuchElementException("box not found");
	}

	public int indegree(Box box) {
		int count = 0;
		for (LinkedList<Box> ll : adj){
			if (ll.contains(box)){
				count++;
			}
		}
		return count;
	}

	/* 
	public Digraph reverse() {
		Digraph reverse = new Digraph(V);
		for (int v = 0; v < V; v++) {
			for (int w : adj(v)) {
				reverse.addEdge(w, v);
			}
		}
		return reverse;
	}
	*/

	/* 
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V + " vertices, " + E + " edges " + NEWLINE);
		for (int v = 0; v < V; v++) {
			s.append(String.format("%d: ", v));
			for (int w : adj[v]) {
				s.append(String.format("%d ", w));
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}
	*/

	public String toDot() {
		StringBuilder s = new StringBuilder();
		s.append("digraph {" + NEWLINE);
		s.append("rankdir = LR;"+ NEWLINE);
		s.append("node [shape = circle];"+ NEWLINE);

		for (LinkedList<Box> ll : adj){
			for (int i = 1; i < ll.size(); i++){
				s.append("\"" + ll.getFirst() + "\" -> \"" + ll.get(i) + "\";" + NEWLINE);
			}
		}

		s.append("}");
		return s.toString();
	}

}
