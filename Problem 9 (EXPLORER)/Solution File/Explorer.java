import java.io.*;
import java.util.*;

class Explorer {
    /**
     * Perform scan queries to find the length of the shortest path from the
     * vertex labeled 1 to the vertex labeled 500 in the graph. Return the
     * length to perform the submit query to submit your answer.
     */
    static int solve() throws IOException {
        // Initialize graph representation
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        graph.put(1, new HashSet<>());
        graph.put(500, new HashSet<>());
        
        // Check for direct connection first
        for (int i = 0; i < 3; i++) {
            int neighbor = scan(1);
            graph.get(1).add(neighbor);
            if (!graph.containsKey(neighbor)) {
                graph.put(neighbor, new HashSet<>());
            }
            graph.get(neighbor).add(1);
            
            if (neighbor == 500) return 1;
        }
        
        for (int i = 0; i < 3; i++) {
            int neighbor = scan(500);
            graph.get(500).add(neighbor);
            if (!graph.containsKey(neighbor)) {
                graph.put(neighbor, new HashSet<>());
            }
            graph.get(neighbor).add(500);
            
            if (neighbor == 1) return 1;
        }
        
        // BFS to find shortest path
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> distance = new HashMap<>();
        
        queue.offer(1);
        distance.put(1, 0);
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int dist = distance.get(current);
            
            // If we haven't fully explored current vertex's neighbors
            if (graph.get(current).size() < 3) {
                for (int i = 0; i < 3; i++) {
                    int neighbor = scan(current);
                    graph.get(current).add(neighbor);
                    
                    if (!graph.containsKey(neighbor)) {
                        graph.put(neighbor, new HashSet<>());
                    }
                    graph.get(neighbor).add(current);
                    
                    if (!distance.containsKey(neighbor)) {
                        distance.put(neighbor, dist + 1);
                        queue.offer(neighbor);
                    }
                    
                    if (neighbor == 500) {
                        return dist + 1;
                    }
                }
            } else {
                // Process already known neighbors
                for (int neighbor : graph.get(current)) {
                    if (!distance.containsKey(neighbor)) {
                        distance.put(neighbor, dist + 1);
                        queue.offer(neighbor);
                        
                        if (neighbor == 500) {
                            return dist + 1;
                        }
                    }
                }
            }
        }
        
        return -1; // This should never happen as graph is connected
    }
    
    /**
     * Scan at the vertex labeled v. Returns the label of a random vertex that v
     * is connected to.
     */
    static int scan(int v) throws IOException {
        out.println("SCAN " + v);
        out.flush();
        String response = in.readLine();
        if (response.equals("WRONG_ANSWER")) {
            System.exit(0);
        }
        return Integer.parseInt(response);
    }

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(in.readLine());
        for (int i = 0; i < T; i++) {
            out.println("SUBMIT " + solve());
            out.flush();
            String response = in.readLine();
            if (response.equals("WRONG_ANSWER")) {
                System.exit(0);
            }
        }
    }
}
