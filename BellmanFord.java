import java.util.*;

public class BellmanFord{

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws Exception{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         */
        
        /* YOUR CODE GOES HERE */
    	this.distances = new int[g.getNbNodes()];
    	this.source = source;
    	this.predecessors = new int[g.getNbNodes()];
    	ArrayList<Edge> totalEdges = g.getEdges();
    	
    	//add edges to ArrayList
    	ArrayList<Integer> nodes = new ArrayList<Integer>(g.getNbNodes());
    	for(Edge e : totalEdges) {
    		nodes.add(e.nodes[0]);
    		nodes.add(e.nodes[1]);
    	}
    	
    	for(Integer node : nodes) {
    		if(node == source) {
    			this.distances[node] = 0;
    		} 
    		else {
    			this.distances[node] = Integer.MAX_VALUE; //represents infinity
    		}
    		
    		this.predecessors[node] = -1;
    	}
    	
    	// relax edges
    	for(int i = 1; i < nodes.size(); i++) {
    		for(Edge e : totalEdges) {
    			int w = distances[e.nodes[0]] + e.weight; //add weight of the edge to the total weight
    			if(w < distances[e.nodes[1]]) {
    				predecessors[e.nodes[1]] = e.nodes[0];
    				distances[e.nodes[1]] = w;
    			}
    		}
    	}
    	
    	// check for negative cycle
    	for(Edge e : totalEdges) {
    		if(distances[e.nodes[0]] + e.weight < distances[e.nodes[1]]) {
    			throw new Exception("Error! Graph contains a negative weight cycle");
    		}
    	}
       
    }

    public int[] shortestPath(int destination) throws Exception{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Error is thrown
         */

        /* YOUR CODE GOES HERE (update the return statement as well!) */
    	int[] path = new int[this.predecessors.length]; //init path
    	path[0] = destination; // path begins at dest, will be reversed later
    	int pLength = 1; // track where we are in the path array
        int pred = destination; // init state for while 
        // loop til we get to a node that has no predecessor in the graph
        
        while((pred = this.predecessors[pred]) != -1) {
        	path[pLength] = pred;
        	pLength++;
        }
        
        // check the path
        if(path[pLength - 1] != this.source) {
        	throw new Exception("Error! Path doesn't exist from given source to destination");
        }
        
        //rev path src -> dest ( what the instructions require)
        int[] revPath = new int[pLength];
        for(int i = 0; i < pLength; i++) {
        	revPath[i] = path[pLength - i - 1];
        }
        
        return revPath;
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}
