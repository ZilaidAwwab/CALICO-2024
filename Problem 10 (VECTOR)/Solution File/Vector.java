import java.io.*;

class Vector {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        // Read input
        int N = Integer.parseInt(br.readLine());
        long[] S = new long[N];
        String[] input = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            S[i] = Long.parseLong(input[i]);
        }

        int Q = Integer.parseInt(br.readLine());
        while (Q-- > 0) {
            String[] query = br.readLine().split(" ");
            String qType = query[0];

            if (qType.equals("UPDATE")) {
                int L = Integer.parseInt(query[1]) - 1; // Convert to 0-based index
                int R = Integer.parseInt(query[2]) - 1; // Convert to 0-based index
                long V = Long.parseLong(query[3]);

                // Update range
                for (int i = L; i <= R; i++) {
                    S[i] += V;
                }
            } else if (qType.equals("FIND")) {
                long minSum = Long.MAX_VALUE;

                // Try each split point
                for (int k = 0; k <= N; k++) {
                    long gcd1 = 0, gcd2 = 0;

                    // Find GCD of first part
                    for (int i = 0; i < k; i++) {
                        gcd1 = gcd(gcd1, S[i]);
                    }

                    // Find GCD of second part
                    for (int i = k; i < N; i++) {
                        gcd2 = gcd(gcd2, S[i]);
                    }

                    // Calculate sum for this split
                    long currSum = 0;

                    // Add quotients from first part
                    for (int i = 0; i < k; i++) {
                        if (gcd1 != 0) {
                            currSum += S[i] / gcd1;
                        }
                    }

                    // Add quotients from second part
                    for (int i = k; i < N; i++) {
                        if (gcd2 != 0) {
                            currSum += S[i] / gcd2;
                        }
                    }

                    minSum = Math.min(minSum, currSum);
                }

                out.println(minSum);
            }
        }

        out.flush();
        out.close();
    }

    // Helper method to calculate GCD
    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
