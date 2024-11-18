import java.io.*;

class Scv {
    static String solve(int N, int M, String[] G) {
        // Get coordinates of all hashtags
        int minRow = N, maxRow = -1, minCol = M, maxCol = -1;
        int hashCount = 0;
        
        // Find boundaries and count hashtags
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (G[i].charAt(j) == '#') {
                    minRow = Math.min(minRow, i);
                    maxRow = Math.max(maxRow, i);
                    minCol = Math.min(minCol, j);
                    maxCol = Math.max(maxCol, j);
                    hashCount++;
                }
            }
        }
        
        // Get shape dimensions
        int height = maxRow - minRow + 1;
        int width = maxCol - minCol + 1;
        
        // Create a clean grid containing just the shape
        boolean[][] shape = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                shape[i][j] = G[i + minRow].charAt(j + minCol) == '#';
            }
        }
        
        // Check for triangle first
        if (isTriangle(shape, height, width, hashCount)) {
            return "phineas";
        }
        
        // If not triangle, must be rectangle
        return "ferb";
    }
    
    private static boolean isTriangle(boolean[][] shape, int height, int width, int hashCount) {
        // Check all four possible orientations of a right triangle
        return isRightTriangleTopLeft(shape, height, width, hashCount) ||
               isRightTriangleTopRight(shape, height, width, hashCount) ||
               isRightTriangleBottomLeft(shape, height, width, hashCount) ||
               isRightTriangleBottomRight(shape, height, width, hashCount);
    }
    
    private static boolean isRightTriangleTopLeft(boolean[][] shape, int height, int width, int hashCount) {
        if (height != width) return false;
        
        int expectedHash = (height * (height + 1)) / 2;
        if (hashCount != expectedHash) return false;
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j <= i; j++) {
                if (!shape[i][j]) return false;
            }
            for (int j = i + 1; j < width; j++) {
                if (shape[i][j]) return false;
            }
        }
        return true;
    }
    
    private static boolean isRightTriangleTopRight(boolean[][] shape, int height, int width, int hashCount) {
        if (height != width) return false;
        
        int expectedHash = (height * (height + 1)) / 2;
        if (hashCount != expectedHash) return false;
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width - i - 1; j++) {
                if (shape[i][j]) return false;
            }
            for (int j = width - i - 1; j < width; j++) {
                if (!shape[i][j]) return false;
            }
        }
        return true;
    }
    
    private static boolean isRightTriangleBottomLeft(boolean[][] shape, int height, int width, int hashCount) {
        if (height != width) return false;
        
        int expectedHash = (height * (height + 1)) / 2;
        if (hashCount != expectedHash) return false;
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j <= height - i - 1; j++) {
                if (!shape[i][j]) return false;
            }
            for (int j = height - i; j < width; j++) {
                if (shape[i][j]) return false;
            }
        }
        return true;
    }
    
    private static boolean isRightTriangleBottomRight(boolean[][] shape, int height, int width, int hashCount) {
        if (height != width) return false;
        
        int expectedHash = (height * (height + 1)) / 2;
        if (hashCount != expectedHash) return false;
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < i; j++) {
                if (shape[i][j]) return false;
            }
            for (int j = i; j < width; j++) {
                if (!shape[i][j]) return false;
            }
        }
        return true;
    }
    
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(in.readLine());
        for (int i = 0; i < T; i++) {
            String[] dimensions = in.readLine().split(" ");
            int N = Integer.parseInt(dimensions[0]), M = Integer.parseInt(dimensions[1]);
            String[] G = new String[N];
            for (int j = 0; j < N; j++) {
                String row = in.readLine();
                G[j] = row;
            }
            out.println(solve(N, M, G));
        }
        out.flush();
    }
}
