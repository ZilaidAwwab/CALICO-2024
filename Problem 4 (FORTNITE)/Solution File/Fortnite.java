import java.io.*;

class Fortnite {
    static double solve(int N, int H, int D, int S, int P) {
        // First check if we can make it by just running
        double timeToRun = (double) D / S;
        double damageWhileRunning = timeToRun * P;
        
        if (damageWhileRunning <= N) {
            return timeToRun;
        }
        
        // If healing rate is less than or equal to damage rate,
        // we can't heal enough to offset damage
        if (H <= P) {
            return -1.0;
        }
        
        // Calculate minimum healing time needed
        double minHealTime = (P * timeToRun - N) / (double)(H - P);
        if (minHealTime < 0) minHealTime = 0;
        
        double totalTime = minHealTime + timeToRun;
        
        // Verify the solution with more precise calculations
        double netHealingRate = H - P;  // Net health gained per second while healing
        double finalHealth = N + (netHealingRate * minHealTime) - (P * timeToRun);
        
        // Add a small epsilon for floating point comparison
        if (finalHealth >= -1e-10) {
            return totalTime;
        }
        
        return -1.0;
    }
    
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);
    
    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(in.readLine());
        for (int t = 0; t < T; t++) {
            String[] params = in.readLine().split(" ");
            int N = Integer.parseInt(params[0]); // starting health
            int H = Integer.parseInt(params[1]); // healing per second
            int D = Integer.parseInt(params[2]); // distance to exit
            int S = Integer.parseInt(params[3]); // running speed
            int P = Integer.parseInt(params[4]); // storm damage per second
            
            double result = solve(N, H, D, S, P);
            if (result == -1.0) {
                out.println("-1.0");
            } else {
                out.println(result);
            }
        }
        out.flush();
    }
}
