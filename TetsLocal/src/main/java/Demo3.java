import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Demo3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] split1 = sc.nextLine().split(" ");
        String[] split2 = sc.nextLine().split(" ");
        int n = Integer.parseInt(split1[0]);
        int k = Integer.parseInt(split1[1]);
        int d = Integer.parseInt(split1[2]);
        int[] scores = new int[n];
        for (int i = 0; i < n; i++) {
            scores[i] = Integer.parseInt(split2[i]);
        }
        List<Integer> res = new ArrayList<>();
        List<List<Integer>> resList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (n - i < k) {
                break;
            }
            List<Integer> tmp = new ArrayList<>();
            tmp.add(scores[i]);
            dfs(scores, i, k, d, tmp, resList, 1);
            for (int j = 0; j < resList.size(); j++) {
                int sum = 1;
                for (int m = 0; m < resList.get(j).size(); m++) {
                    sum *= resList.get(j).get(m);
                }
                res.add(sum);
            }
        }
        Collections.sort(res);
        System.out.println(res.get(res.size() - 1));
    }

    public static void dfs(int[] scores, int index, int k, int d, List<Integer> tmp, List<List<Integer>> resList, int count) {
        if (index == scores.length - 1 || count == k) {
            if (tmp.size() == k) {
                resList.add(new ArrayList<>(tmp));
            }
            return;
        }
        for (int i = 1; i <= d; i++) {
            if (index + i < scores.length) {
                tmp.add(scores[index + i]);
                dfs(scores, index + i, k, d, tmp, resList, ++count);
                tmp.remove(tmp.size() - 1);
            }
        }
    }
}
