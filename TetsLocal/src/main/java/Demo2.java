import java.sql.SQLOutput;
import java.util.Scanner;

public class Demo2 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String numStr=sc.nextLine();
        if(numStr.length()<=1){
            System.out.println("NO");
            return;
        }
        int tmpStr = Integer.parseInt(numStr);
        numStr=String.valueOf(tmpStr);
        String[] split = numStr.split("");
        int[] num=new int[split.length];
        for(int i=0;i<split.length;i++){
            num[i]=Integer.parseInt(split[i]);
        }
        for(int i=0;i< num.length;i++){
            int[] vals = count(num, i);
            if(vals[0]==vals[1]){
                System.out.println("YES");
                return;
            }
        }
        System.out.println("NO");
    }

    public static int[] count(int[] num,int cur){
        int[] res={1,1};
        for(int i=0;i<=cur;i++){
            res[0]*=num[i];
        }
        for(int i=cur+1;i< num.length;i++){
            res[1]*=num[i];
        }
        return res;
    }
}
