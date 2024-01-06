package loop_progs;
import java.util.*;
public class upto_n
{
    public static void main(String[] args)
    {
        int i,n;

        Scanner sc =new Scanner(System.in);
        System.out.println("Input value of 'n' ");
        n=sc.nextInt();
        i=1;
        while(i<=n){
            System.out.println("i= "+i);
            i++;
        }

    }
}
