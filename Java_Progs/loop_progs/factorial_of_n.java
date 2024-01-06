package loop_progs;

import java.util.Scanner;

public class factorial_of_n
{
    public static void main(String[] args)
    {
        Scanner sc =new Scanner(System.in);
        int p=1;
        int i=1;
        int n;
        System.out.println("Input value to calculate its factorial ");
        n=sc.nextInt();
        while(i<=n){
            p=p*i;
            i++;
        }
        System.out.println("Factorial of "+ n +" = "+ p);
    }
}