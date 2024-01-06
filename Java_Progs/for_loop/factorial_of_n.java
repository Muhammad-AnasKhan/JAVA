package for_loop;//package loop_progs;
import java.util.Scanner;
public class factorial_of_n
{
    public static void main(String[] args)
    {
        Scanner sc =new Scanner(System.in);
        int fac=1;
        int n;
        System.out.println("Input value to calculate its factorial ");
        n=sc.nextInt();
        for (int i=1;i<=n;i++){
            fac=fac*i;
        }
        System.out.println("Factorial of "+ n +" = "+ fac);
    }
}