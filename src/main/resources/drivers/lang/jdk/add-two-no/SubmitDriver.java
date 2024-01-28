import java.util.Scanner;
class Solution {
    public static void main(String[] args) {
        Problem sol = new Problem();
        Scanner sc = new Scanner(System.in);
        int testCases = sc.nextInt();

        try {
            for (int i = 0; i < testCases; i++) {
                int ans = sol.add(sc.nextInt(), sc.nextInt());
                int expected = sc.nextInt();
                if(ans != expected) throw new RuntimeException(String.valueOf(i+1) + "~" + ans + "~" + expected);
            }
            System.out.println("Pass");
        }catch (RuntimeException exception) {
            System.out.println("Failed :" + exception.getMessage());
        }
    }
}

// user code will go below
// in append mode