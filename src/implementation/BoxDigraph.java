package implementation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BoxDigraph {
	private static final String NEWLINE = System.getProperty("line.separator");

	private final int V; // number of vertices in this digraph
	private int E; // number of edges in this digraph
	private List<LinkedList<Box>> adj; // adj[v] = adjacency list for vertex v
	private HashMap<Box, Boolean> indegreeMemo;

	public BoxDigraph(int V) {
		if (V < 0)
			throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");

		this.V = V;
		this.E = 0;
		adj = new ArrayList<LinkedList<Box>>();
		indegreeMemo = new HashMap<>();
	}

	public BoxDigraph(Iterable<Box> catalog, int V) {
		if (V < 0)
			throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");

		this.V = V;
		this.E = 0;
		adj = new ArrayList<LinkedList<Box>>();
		indegreeMemo = new HashMap<>();

		// insert vertices
		for(Box box : catalog){
			addVertex(box);
		}

		System.out.println("# vertices added to graph");

		// make vertices adjecent to others by adding edges
		for(int i = 0; i < adj.size(); i++){
			Box vertice = adj.get(i).getFirst();
			for (Box possibleEdge : catalog){
				if (validateBoxes(vertice, possibleEdge)){
					this.addEdge(vertice, possibleEdge);
					this.updateIndegreeMemo(possibleEdge);
				}	
			}
			clearConsole();
			System.out.println("# added all possible adjacencies to " + (i + 1) + "º vertex");
		}
	}

	private void updateIndegreeMemo(Box box){
		indegreeMemo.putIfAbsent(box, true);
	}

	public int getV() {
		return V;
	}

	public int getE() {
		return E;
	}

	private boolean validateBoxes(Box box1, Box box2) {
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
		if (validateBoxes(b1, b2)){
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
	}

	public LinkedList<Box> getAdjacencies(Box box) {
		for (LinkedList<Box> ll : adj){
			if (ll.getFirst().equals(box)){
				return ll;
			}
		}

		throw new NoSuchElementException("box not found");
	}

	private void dfs(Box box, HashSet<Box> visited, Stack<Box> orderedGraph){
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

	public Integer getLongestPathSize() {
		HashSet<Box> visited = new HashSet<>();
		Integer maxPath = 0;
		
		for(LinkedList<Box> ll : adj){
			Box b = ll.getFirst();

			if (!indegreeMemo.containsKey(b)) {
				HashMap<Box, Integer> maxPaths = getLongestPathsFrom(b);
				visited.addAll(maxPaths.keySet());
				Integer maxValueFound = getMaxValue(maxPaths);

				if (maxPath < maxValueFound) {
					//System.out.println("bigger path found = " + maxValueFound);
					maxPath = maxValueFound;
				}
			}
		}
		
		return maxPath;
	}

	private Integer getMaxValue(HashMap<Box, Integer> map) {
		return map.values().stream().max(Integer::compareTo).orElse(0);
	}

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

	private static void clearConsole() {  
		System.out.print("\033[H\033[2J");  
		System.out.flush();  
	} 

}
