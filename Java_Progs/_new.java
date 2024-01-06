import java.util.*;
public class _new{
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        int a[]=new int[10];
        System.out.println("Enter 10 values to save in array");
        int even=0,odd=0;
        for(int i=0;i<a.length;i++){
            a[i]=sc.nextInt();
            if (a[i]%2==0)
                even+=1;
            else
                odd+=1;
        }
        System.out.println("No.of Even= "+even);
        System.out.println("No.of odd= "+odd);
    }
}






























































// public class _new {
//     public static void main(String[] args) {
//         //      for loop in java
// //     int i=1;
//         int s = 0;
//         int n = 2;
//         for (int i = 1; i <= n; i++) {
//             s = s + i;
//             System.out.println(s);
//         }
//     }
// }