import java.io.*;
import java.util.*;

class Bridge {
    static int solve(int B, int N, int[] heights) {
        int maxHeight = Arrays.stream(heights).max().orElse(0);

        int optimalHeight = -1;
        int minDanger = Integer.MAX_VALUE;
        int minCost = Integer.MAX_VALUE;

        // Iterate over all possible bridge heights
        for (int h = 0; h <= maxHeight; h++) {
            int danger = 0;
            int cost = 0;

            // Calculate danger and cost for the current height
            for (int height : heights) {
                danger += Math.max(0, h - height);
                cost += Math.max(0, height - h);
            }

            // Check if the cost is within the budget
            if (cost <= B) {
                if (danger < minDanger || (danger == minDanger && cost < minCost)) {
                    minDanger = danger;
                    minCost = cost;
                    optimalHeight = h;
                }
            }
        }

        return optimalHeight;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < T; t++) {
            String[] firstLine = br.readLine().trim().split(" ");
            int B = Integer.parseInt(firstLine[0]);
            int N = Integer.parseInt(firstLine[1]);

            String[] secondLine = br.readLine().trim().split(" ");
            int[] heights = Arrays.stream(secondLine).mapToInt(Integer::parseInt).toArray();

            sb.append(solve(B, N, heights)).append("\n");
        }

        System.out.print(sb);
    }
}
