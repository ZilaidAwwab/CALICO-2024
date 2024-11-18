import java.io.*;
import java.util.*;

class Torreznos {
    /**
     * Output the minimum cost to build roads such that France can reach all countries
     * without going through Spain and vice versa.
     */
    static void solve(int N, int M, int F, int S, int[][] E) {
        // Special case: if only France and Spain exist
        if (N == 2) {
            out.println(0);
            return;
        }
        
        // Create adjacency list representation of the graph
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<>());
        }
        
        // Add all edges to the graph (undirected)
        for (int[] e : E) {
            adj.get(e[0]).add(new Edge(e[1], e[2]));
            adj.get(e[1]).add(new Edge(e[0], e[2]));
        }
        
        // Try all possible combinations of roads
        int minTotalCost = Integer.MAX_VALUE;
        for (int mask = 0; mask < (1 << M); mask++) {
            int cost = 0;
            // Create current graph based on selected edges
            List<List<Integer>> currGraph = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                currGraph.add(new ArrayList<>());
            }
            
            // Add selected edges to current graph
            for (int i = 0; i < M; i++) {
                if ((mask & (1 << i)) != 0) {
                    int u = E[i][0], v = E[i][1], w = E[i][2];
                    currGraph.get(u).add(v);
                    currGraph.get(v).add(u);
                    cost += w;
                }
            }
            
            // Check if this combination of roads satisfies our conditions
            if (isValid(currGraph, N, F, S)) {
                minTotalCost = Math.min(minTotalCost, cost);
            }
        }
        
        out.println(minTotalCost);
    }
    
    // Check if the current graph configuration is valid
    static boolean isValid(List<List<Integer>> graph, int N, int F, int S) {
        // Check if France can reach all vertices except Spain
        boolean[] visitedF = new boolean[N];
        visitedF[S] = true;  // Mark Spain as visited to avoid going through it
        dfs(graph, F, visitedF);
        
        // Check if Spain can reach all vertices except France
        boolean[] visitedS = new boolean[N];
        visitedS[F] = true;  // Mark France as visited to avoid going through it
        dfs(graph, S, visitedS);
        
        // Check if all vertices (except Spain) are reachable from France
        for (int i = 0; i < N; i++) {
            if (i != S && !visitedF[i]) return false;
        }
        
        // Check if all vertices (except France) are reachable from Spain
        for (int i = 0; i < N; i++) {
            if (i != F && !visitedS[i]) return false;
        }
        
        return true;
    }
    
    // DFS to mark all reachable vertices
    static void dfs(List<List<Integer>> graph, int vertex, boolean[] visited) {
        visited[vertex] = true;
        for (int next : graph.get(vertex)) {
            if (!visited[next]) {
                dfs(graph, next, visited);
            }
        }
    }
    
    static class Edge {
        int to, weight;
        
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);
    
    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(in.readLine());
        for (int i = 0; i < T; i++) {
            String[] info = in.readLine().split(" ");
            int N = Integer.parseInt(info[0]);
            int M = Integer.parseInt(info[1]);
            int F = Integer.parseInt(info[2]);
            int S = Integer.parseInt(info[3]);
            int[][] E = new int[M][];
            for (int j = 0; j < M; j++) {
                String[] trail = in.readLine().split(" ");
                int[] e = new int[3];
                e[0] = Integer.parseInt(trail[0]);
                e[1] = Integer.parseInt(trail[1]);
                e[2] = Integer.parseInt(trail[2]);
                E[j] = e;
            }
            solve(N, M, F, S, E);
        }
        out.flush();
    }
}
