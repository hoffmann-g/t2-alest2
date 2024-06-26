package implementation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

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
				if(this.validateBox(b1, b2)){
					this.addEdge(b1, b2);
					E++;
				} else if (b1.equals(b2)){
					this.addVertex(b1);
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
		if(this.validateBox(b1, b2)){
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

	public void dfs(Box box, HashSet<Box> visited, Stack<Box> orderedGraph){
		visited.add(box);

		for (Box b : this.getAdjacencies(box)){
			if (!visited.contains(b)){
				dfs(b, visited, orderedGraph);
			}
		}
		orderedGraph.push(box);
	}

	public Stack<Box> topologicalSearch(Box box){

		// tracks if a node was visited, or not
		HashSet<Box> visited = new HashSet<>();
		// ordered-graph
		Stack<Box> orderedGraph = new Stack<>();

		for(LinkedList<Box> ll: adj){
			Box b = ll.getFirst();
			if (!visited.contains(b)){
				dfs(b, visited, orderedGraph);
			}
		}

		return orderedGraph;
	}

	public HashMap<Box, Integer> getLongestPathsFrom(Box box) {
		// perform topological sorting
		Stack<Box> orderedGraph = topologicalSearch(box);
	
		// initialize distances map with -infinite for all vertices
		HashMap<Box, Integer> distances = new HashMap<>();
		for (LinkedList<Box> ll : adj) {
			distances.put(ll.getFirst(), Integer.MIN_VALUE);
		}
		distances.put(box, 0); // Starting vertex has distance 0
	
		// process vertices in topological order
		while (!orderedGraph.isEmpty()) {
			Box b = orderedGraph.pop();

			int currentDistance = distances.get(b);

			// if the current distance is reachable
			if (currentDistance != Integer.MIN_VALUE) {

				//System.out.println("current box: " + b);
				//System.out.println("currentDistance: " + currentDistance);
				//System.out.println("distances: " + distances);

				// update distances for adjacent vertices
				for (Box adjBox : getAdjacencies(b)) {
					int newDistance = currentDistance + 1; // increase distance by 1
					if (distances.get(adjBox) < newDistance) {
						distances.put(adjBox, newDistance);

					}
				}
			}
		}

		return distances;
	}

	public int getLongestPathSize() {

		HashMap<Box, Integer> longestPaths = new HashMap<>();
		int maxValue = Integer.MIN_VALUE;

		for (LinkedList<Box> ll : adj){
			longestPaths.putAll(getLongestPathsFrom(ll.getFirst()));

			for (Map.Entry<Box, Integer> entry : longestPaths.entrySet()) {
				if (entry.getValue() > maxValue) {
					maxValue = entry.getValue();
				}
			}
		}

		return maxValue;		
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
