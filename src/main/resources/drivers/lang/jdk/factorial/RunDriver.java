import java.util.Scanner;

class Solution {
    public static void main(String[] args) {
        Problem sol = new Problem();
        Scanner sc = new Scanner(System.in);
        int testCases = sc.nextInt();
        Answer ans = new Answer();

        for (int i = 0; i < testCases; i++) {
            int n = sc.nextInt();
            long submitted = sol.factorial(n);
            long expectedAns = ans.factorial(n);
            if(submitted == expectedAns) {
                System.out.println("Pass:"+ submitted + "~"+ expectedAns);
            }else {
                System.out.println("Fail:"+ submitted + "~"+ expectedAns);
            }
        }
    }
}

class Answer {
    public long factorial(int n) {
        if(n == 0 || n == 1) return 1;
        long fact = 1;
        for(int i=2; i<= n; i++) fact *= i;
        return fact;
    }
}

// user code will go below
// in append mode