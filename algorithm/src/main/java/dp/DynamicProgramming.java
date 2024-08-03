package dp;

public class DynamicProgramming {
    public static void main(String[] args) {
        System.out.println(jumpFloor(7));
    }

    public static int jumpFloor(int number) {
        // write code here
        int[] dp = new int[number + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= number; i++) {
            dp[i] = dp[i - 1]+ dp[i - 2];
        }
        return dp[number];
    }
}
