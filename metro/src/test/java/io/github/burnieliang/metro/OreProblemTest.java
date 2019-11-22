package io.github.burnieliang.metro;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Boning Liang
 * @date 2019-11-22 13:28:42
 */
public class OreProblemTest {

    @Test
    public void test() {
        int[] input = {3, 7, 4, 11, 8, 10};
        System.out.println(solution(input));

        int[] input2 = {25,25,20,15,15,60,55,50};
        System.out.println(solution(input2));
    }

    private int solution(int[] input) {
        int sum = 0;
        for (int i : input) {
            sum+=i;
        }
        int n = input.length;
        int min = 100000;
        int answer = 0;
        int maxN = (input.length + 1) / 2;
        int maxWeight = (sum + 1) / 2;
        boolean[][] dp = new boolean[110][10010];
        dp[0][0] = true;

        for (int value : input) {
            for (int j = sum; j >= value; j--) {
                for (int h = n; h >= 0; h--) {
                    if (dp[h][j - value]) {
                        dp[h + 1][j] = true;
                        if (h + 1 == maxN) {
                            if (Math.abs(j - maxWeight) < min) {
                                min = Math.abs(j - maxWeight);
                                answer = j;
                            }
                        }
                    }
                }
            }
        }
        return Math.abs(sum - answer - answer);
    }

    private void print(boolean[][] dp) {
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                if (dp[i][j] == true) {
                    System.out.println( i + " " + j + " " + dp[i][j] + " ");
                }
            }
        }
        System.out.println(" ");
    }
}