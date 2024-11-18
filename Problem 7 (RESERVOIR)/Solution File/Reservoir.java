import java.util.Scanner;

class Reservoir {
    /**
     * Return the maximum number of islands.
     *
     * N: number of rows
     * M: number of columns
     * G: grid of heights
     */
    public static int solve(int N, int M, int[][] G) {
        int minHeight = Integer.MAX_VALUE;
        int maxHeight = Integer.MIN_VALUE;
    
        // Find min and max heights
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                minHeight = Math.min(minHeight, G[i][j]);
                maxHeight = Math.max(maxHeight, G[i][j]);
            }
        }
    
        int maxIslands = 0;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    
        // Try every possible water level
        for (int h = minHeight; h <= maxHeight; h++) {
            boolean[][] visited = new boolean[N][M];
            int islandCount = 0;
    
            // Find islands for this water level
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (G[i][j] >= h && !visited[i][j]) {
                        dfsIsland(G, visited, i, j, h, N, M, directions);
                        islandCount++;
                    }
                }
            }
            maxIslands = Math.max(maxIslands, islandCount);
        }
    
        return maxIslands;
    }
    
    private static void dfsIsland(int[][] grid, boolean[][] visited, int x, int y, int h, int N, int M, int[][] directions) {
        visited[x][y] = true;
    
        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
    
            if (nx >= 0 && nx < N && ny >= 0 && ny < M && grid[nx][ny] >= h && !visited[nx][ny]) {
                dfsIsland(grid, visited, nx, ny, h, N, M, directions);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int T = sc.nextInt(); // Number of test cases
        for (int t = 0; t < T; t++) {
            int N = sc.nextInt(); // Number of rows
            int M = sc.nextInt(); // Number of columns
            int[][] G = new int[N][M];
            
            // Reading the grid
            for (int i = 0; i < N; i++)
                for (int j = 0; j < M; j++)
                    G[i][j] = sc.nextInt();
            
            // Call the solve function and print the result
            System.out.println(solve(N, M, G));
        }
        
        sc.close();
    }
}
