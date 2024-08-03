package recursion;

public class RecursionDemo {
    static final int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};// 上下左右

    public static void main(String[] args) {
        int[][] grid1 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
        char[][] grid = new char[grid1.length][grid1[0].length];
        for (int i = 0; i < grid1.length; i++) {
            for (int j = 0; j < grid1[0].length; j++) {
                grid[i][j] =  String.valueOf(grid1[i][j]).charAt(0);
            }
        }
        int solve = solve(grid);
        System.out.println(solve);
    }

    public static int solve(char[][] grid) {
        // write code here
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (visited[i][j] || grid[i][j] == '0') {
                    visited[i][j] = true;
                    continue;
                }
                if (!visited[i][j] && grid[i][j] == '1') {
                    visited[i][j] = true;
                    dfs(grid, visited, i, j);
                    res++;
                }
            }
        }
        return res;
    }

    public static void dfs(char[][] grid, boolean[][] visited, int i, int j) {
        for (int[] dir : dirs) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            if (newI >= 0 && newI < grid.length && newJ >= 0 && newJ < grid[0].length && !visited[newI][newJ] && grid[newI][newJ] == '1') {
                visited[newI][newJ] = true;
                dfs(grid, visited, newI, newJ);
            }
        }
    }
}
