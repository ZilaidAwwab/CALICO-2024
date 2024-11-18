import java.io.*; 
 
class CaliConstruction { 
    /** 
     * Return the minimum number of sets of CALICO blocks needed to 
     * form S, or -1 if it is impossible. 
     * 
     * S: a string 
     */ 
    static int solve(String S) { 
        // Arrays to store character counts
        int[] charCount = new int[26];  // Count of each character in input
        int[] modelCount = new int[26]; // Count needed for one CALICO set
        
        // Count characters in input string
        for (char c : S.toCharArray()) {
            charCount[c - 'A']++;
        }
        
        // Map characters to their corresponding CALICO position
        for (char c : "CALICO".toCharArray()) {
            modelCount[c - 'A']++;
        }
        
        // Special mappings
        // C, N, U map to C
        int totalCCount = charCount['C' - 'A'] + charCount['N' - 'A'] + charCount['U' - 'A'];
        charCount['C' - 'A'] = totalCCount;
        
        // H maps to I
        charCount['I' - 'A'] += charCount['H' - 'A'];
        
        // Check if string can be formed
        int maxSets = 0;
        
        // Check for each CALICO character
        for (char c : "CALICO".toCharArray()) {
            int required = charCount[c - 'A'];
            int inOneSet = modelCount[c - 'A'];
            
            if (inOneSet == 0) continue;
            
            // Calculate sets needed for this character
            int setsNeeded = (required + inOneSet - 1) / inOneSet;
            maxSets = Math.max(maxSets, setsNeeded);
        }
        
        // Verify solution
        for (int i = 0; i < 26; i++) {
            char currentChar = (char)('A' + i);
            // Skip if character is part of special mappings
            if (currentChar == 'N' || currentChar == 'U' || currentChar == 'H') continue;
            // Skip if character is part of CALICO
            if ("CALICO".indexOf(currentChar) >= 0) continue;
            // If any other character exists in input, return -1
            if (charCount[i] > 0) return -1;
        }
        
        return maxSets;
    }
 
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
    static PrintWriter out = new PrintWriter(System.out); 
 
    public static void main(String[] args) throws IOException { 
        int T = Integer.parseInt(in.readLine()); 
        for (int i = 0; i < T; i++) { 
            String S = in.readLine(); 
            out.println(solve(S)); 
        } 
        out.flush(); 
    } 
}
