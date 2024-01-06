package for_loop;//package loop_progs;
import java.util.*;
public class sum_upto_n
{
    public static void main(String[] args)
    {
        int i,s=0,n;

        Scanner sc =new Scanner(System.in);
        System.out.println("Input value of 'n' to sum ");
        n=sc.nextInt();
        for (i=0;i<=n;i++){
            s=s+i;
        }
        System.out.println("Sum upto "+ n +" = "+s);
    }
}