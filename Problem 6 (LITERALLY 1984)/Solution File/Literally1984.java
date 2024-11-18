import java.io.*;
import java.util.*;

class Literally1984 {
    static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    static List<Integer> solve(int N) {
        int upperLimit = (int) Math.ceil(Math.sqrt(N * 10L));
        List<int[]> visibleHouses = new ArrayList<>();
        
        // Generate all possible (x, y) coordinates
        for (int x = 1; x <= upperLimit; x++) {
            for (int y = 1; y <= upperLimit; y++) {
                if (gcd(x, y) == 1) {  // Only consider coprime pairs
                    visibleHouses.add(new int[]{x, y});
                }
            }
        }
        
        // Sort by Manhattan distance (x + y), then by x
        Collections.sort(visibleHouses, (a, b) -> {
            int distA = a[0] + a[1];
            int distB = b[0] + b[1];
            if (distA != distB) {
                return Integer.compare(distA, distB);
            }
            return Integer.compare(a[0], b[0]);
        });
        
        // Return the Nth house coordinates
        int[] house = visibleHouses.get(N - 1);
        return Arrays.asList(house[0], house[1]);
    }
    
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(in.readLine());
        for (int i = 0; i < T; i++) {
            int N = Integer.parseInt(in.readLine());
            List<Integer> ans = solve(N);
            out.println(ans.get(0) + " " + ans.get(1));
        }
        out.flush();
    }
}
