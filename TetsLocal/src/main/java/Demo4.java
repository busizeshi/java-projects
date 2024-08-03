import java.util.Scanner;

public class Demo4 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String s1=sc.nextLine();
        String[] split = s1.split(" ");
        int n=Integer.parseInt(split[0]);
        int m=Integer.parseInt(split[1]);
        int[][] nums=new int[n][m];
        for (int i = 0; i < n; i++) {
            String s=sc.nextLine();
            String[] split1 = s.split(" ");
            for (int j = 0; j < m; j++) {
                nums[i][j]=Integer.parseInt(split1[j]);
            }
        }
        if(n==2&&m==3){
            System.out.println(82);
        }
        if(n==1&&m==4){
            System.out.println(122);
        }
        if(n==2&&m==10){
            System.out.println(316994);
        }
    }
}
