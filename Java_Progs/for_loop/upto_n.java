package for_loop;//package for_loop;
import java.util.*;
public class upto_n
{
    public static void main(String[] args)
    {
        Scanner sc =new Scanner(System.in);
        System.out.println("Input value of 'n' ");
        int n;
        n=sc.nextInt();
        for(int i=1;i<=n;i++)
        {
            System.out.println("i= "+i);
        }
    }
}
