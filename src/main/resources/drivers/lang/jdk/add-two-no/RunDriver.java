import java.util.Scanner;

class Solution {
    public static void main(String[] args) {
        Problem sol = new Problem();
        Scanner sc = new Scanner(System.in);
        int testCases = sc.nextInt();
        Answer ans = new Answer();

        for (int i = 0; i < testCases; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int submitted = sol.add(a, b);
            int expectedAns = ans.add(a, b);
            if(submitted == expectedAns) {
                System.out.println("Pass:"+ submitted + "~"+ expectedAns);
            }else {
                System.out.println("Fail:"+ submitted + "~"+ expectedAns);
            }
        }
    }
}

class Answer {
    public int add(int a, int b) {
        return a+b;
    }
}

// user code will go below
// in append mode