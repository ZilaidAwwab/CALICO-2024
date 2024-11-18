import java.io.*;
import java.util.*;

class Celeste {
    // Class to represent a state in the BFS
    static class State {
        int row, col;   // Current position
        boolean isRed;  // Hair color state (true = red, false = blue)
        int moves;      // Number of moves taken to reach this state
        
        State(int row, int col, boolean isRed, int moves) {
            this.row = row;
            this.col = col;
            this.isRed = isRed;
            this.moves = moves;
        }
    }
    
    // Directions: up, right, down, left
    static final int[] dr = {-1, 0, 1, 0};
    static final int[] dc = {0, 1, 0, -1};
    
    static int solve(int N, int M, int K, String[] C) {
        // Find start and end positions
        int startRow = -1, startCol = -1;
        int endRow = -1, endCol = -1;
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (C[i].charAt(j) == 'S') {
                    startRow = i;
                    startCol = j;
                } else if (C[i].charAt(j) == 'E') {
                    endRow = i;
                    endCol = j;
                }
            }
        }
        
        // BFS queue and visited set
        Queue<State> queue = new LinkedList<>();
        // Use a boolean array to track visited states (position + hair color)
        boolean[][][] visited = new boolean[N][M][2];
        
        // Start with red hair
        queue.offer(new State(startRow, startCol, true, 0));
        visited[startRow][startCol][1] = true;
        
        while (!queue.isEmpty()) {
            State current = queue.poll();
            
            // Check if we've reached the end
            if (current.row == endRow && current.col == endCol) {
                return current.moves;
            }
            
            // Try walking in all four directions
            for (int dir = 0; dir < 4; dir++) {
                int newRow = current.row + dr[dir];
                int newCol = current.col + dc[dir];
                
                // Check if the new position is valid
                if (isValid(newRow, newCol, N, M, C)) {
                    boolean newIsRed = C[newRow].charAt(newCol) == '*' ? true : current.isRed;
                    
                    if (!visited[newRow][newCol][newIsRed ? 1 : 0]) {
                        visited[newRow][newCol][newIsRed ? 1 : 0] = true;
                        queue.offer(new State(newRow, newCol, newIsRed, current.moves + 1));
                    }
                }
            }
            
            // Try dashing if hair is red
            if (current.isRed) {
                for (int dir = 0; dir < 4; dir++) {
                    boolean foundCrystal = false;
                    int newRow = current.row;
                    int newCol = current.col;
                    
                    // Simulate the dash movement
                    for (int step = 1; step <= K; step++) {
                        newRow += dr[dir];
                        newCol += dc[dir];
                        
                        // Stop if we hit a wall
                        if (!isValid(newRow, newCol, N, M, C)) {
                            newRow -= dr[dir];
                            newCol -= dc[dir];
                            break;
                        }
                        
                        // Check for crystal during dash
                        if (C[newRow].charAt(newCol) == '*') {
                            foundCrystal = true;
                        }
                    }
                    
                    // Only add new state if we moved at least one tile
                    if (newRow != current.row || newCol != current.col) {
                        // Hair will be red if we passed through a crystal, blue otherwise
                        boolean newIsRed = foundCrystal;
                        
                        if (!visited[newRow][newCol][newIsRed ? 1 : 0]) {
                            visited[newRow][newCol][newIsRed ? 1 : 0] = true;
                            queue.offer(new State(newRow, newCol, newIsRed, current.moves + 1));
                        }
                    }
                }
            }
        }
        
        return -1; // No solution found
    }
    
    // Helper method to check if a position is valid
    static boolean isValid(int row, int col, int N, int M, String[] C) {
        return row >= 0 && row < N && col >= 0 && col < M && C[row].charAt(col) != '#';
    }

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(in.readLine());
        for (int tc = 0; tc < T; tc++) {
            String[] temp = in.readLine().split(" ");
            int N = Integer.parseInt(temp[0]);
            int M = Integer.parseInt(temp[1]);
            int K = Integer.parseInt(temp[2]);
            String[] C = new String[N];
            for (int i = 0; i < N; ++i) {
                C[i] = in.readLine();
            }
            out.println(solve(N, M, K, C));
        }
        out.flush();
    }
}
