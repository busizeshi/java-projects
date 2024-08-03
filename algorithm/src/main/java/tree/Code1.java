package tree;

import java.util.Stack;

public class Code1 {
    public static void main(String[] args) {
        String s = "This is a sample";
        System.out.println(trans(s, s.length()));
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param s string字符串
     * @param n int整型
     * @return string字符串
     */
    public static String trans(String s, int n) {
        boolean[] flag = new boolean[2];

        if (s.charAt(0) == ' ') {
            flag[0] = true;
        }
        if (s.charAt(n - 1) == ' ') {
            flag[1] = true;
        }

        StringBuilder res = new StringBuilder();
        Stack<String> stack = new Stack<>();
        String[] split = s.split(" ");
        for (String tmpStr : split) {
            StringBuilder tmp = new StringBuilder();
            for (int j = 0; j < tmpStr.length(); j++) {
                char[] charArray = tmpStr.toCharArray();
                if (Character.isUpperCase(charArray[j])) {
                    charArray[j] = Character.toLowerCase(charArray[j]);
                } else {
                    charArray[j] = Character.toUpperCase(charArray[j]);
                }
                tmp.append(charArray[j]);
            }
            stack.push(String.valueOf(tmp));
        }

        while (!stack.isEmpty()) {
            res.append(stack.pop()).append(" ");
        }
        if (flag[0]) {
            res.append(" ");
        }
        if (flag[1]) {
            res.append(new StringBuilder(" ").append(res));
        }
        return res.toString().trim();
    }
}
