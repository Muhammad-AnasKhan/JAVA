//package loop_progs;
import java.util.*;
public class sum_upto_n
{
    public static void main(String[] args)
    {
        int i,s,n;

        Scanner sc =new Scanner(System.in);
        System.out.println("Input value of 'n' to sum ");
        n=sc.nextInt();
        i=1;
        s=0;
        do {
            s=s+i;
            i++;
        }while(i<=n);
        System.out.println("Sum upto "+ n +" = "+s);

    }
}