package com.github.amitsureshchandra.leetcodeclone.driver.java;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Solution sol = new Solution();
        Scanner sc = new Scanner(System.in);
        int testCases = sc.nextInt();

        try {
            for (int i = 0; i < testCases; i++) {
                int ans = sol.add(sc.nextInt(), sc.nextInt());
                if(ans != sc.nextInt()) throw new RuntimeException(String.valueOf(i+1));
            }
            System.out.println("Pass");
        }catch (RuntimeException exception) {
            System.out.println("Failed :" + exception.getMessage());
        }
    }
}

class Solution {
    public int add(int a, int b) {
        return a+b;
    }
}


