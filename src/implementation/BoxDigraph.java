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

	private int V; // number of vertices in this digraph
	private int E; // number of edges in this digraph
	private List<LinkedList<Box>> adj; // adj[v] = adjacency list for vertex v
	private HashSet<Box> indegreeMemo;

	public BoxDigraph() {
		if (V < 0)
			throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");

		this.V = 0;
		this.E = 0;
		this.adj = new ArrayList<LinkedList<Box>>();
		this.indegreeMemo = new HashSet<>();
	}

	public BoxDigraph(Iterable<Box> catalog) {
		if (V < 0)
			throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");

		this.V = 0;
		this.E = 0;
		this.adj = new ArrayList<LinkedList<Box>>();
		this.indegreeMemo = new HashSet<>();

		// insert vertices
		for(Box box : catalog){
			addVertex(box);
		}

		// make vertices adjecent to others by adding edges
		for(int i = 0; i < adj.size(); i++){
			Box vertice = adj.get(i).getFirst();
			for (Box possibleEdge : catalog){
				if (validateBoxes(vertice, possibleEdge)){
					this.addEdge(i, vertice, possibleEdge);
					// since the possible vertex is going to have another vertex directed to it,
					// we keep track of it in a hashset
					this.updateIndegreeMemo(possibleEdge);
				}	
			}
			// console log purpose ---------------------------------------------------------------------------
			clearConsole();
			System.out.println("# Adding all possible adjacencies to vertices (" + (i + 1) + "|" + V + ")");
			// -----------------------------------------------------------------------------------------------
		}
	}

	private void updateIndegreeMemo(Box box){
		indegreeMemo.add(box);
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
		this.V++;
	}

	public void addEdge(Box b1, Box b2) {
		if (validateBoxes(b1, b2)){
			for (LinkedList<Box> ll : adj){
				if (ll.getFirst().equals(b1)){
					ll.add(b2);
					this.E++;
					return;
				}
			}
	
			LinkedList<Box> newAdj = new LinkedList<Box>();
			newAdj.add(b1);
			newAdj.add(b2);
	
			adj.add(newAdj);
		}		
	}

	public void addEdge(int i, Box b1, Box b2) {
		if (validateBoxes(b1, b2)){
			if (adj.get(i).getFirst() == b1)
				adj.get(i).add(b2);
				this.E++;
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
		Integer maxPath = 0;

		// console log purpose -----------------
		int index = 1;
		int total = (V - indegreeMemo.size());
		//  ------------------------------------

		for(LinkedList<Box> ll : adj){
			Box b = ll.getFirst();
			
			if (!indegreeMemo.contains(b)) {
				HashMap<Box, Integer> maxPaths = getLongestPathsFrom(b);
				Integer maxValueFound = getMaxValue(maxPaths);

				// console log purpose
				 index++;
				// -------------------

				if (maxPath < maxValueFound) {
					maxPath = maxValueFound;
					
					// console log purpose ----------------------------------------------------------------------
					clearConsole();
					System.out.println("# Adding all possible adjacencies to vertices (" + V + "|" + V + ")");
					System.out.println("# Searching for longest paths (" + index + "|" + total + ")");
					//  -----------------------------------------------------------------------------------------
				}

				// console log purpose --------------------------------------------------------------------------
				if (index == (V - indegreeMemo.size())){
					  clearConsole();
					  System.out.println("# Adding all possible adjacencies to vertices (" + V + "|" + V + ")");
					  System.out.println("# Searching for longest paths (" + total + "|" + total + ")");
				}
				//  ----------------------------------------------------------------------------------------------
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

	// console log purpose -------------------
	private static void clearConsole() {  
		System.out.print("\033[H\033[2J");  
		System.out.flush();  
	} 
	// ---------------------------------------

}
