package loop_progs;

import java.util.Scanner;

public class prime_composite{
    public static void main(String[] args){
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter a number to check whether it is prime or composite");
        int n;
        int i=2;
        int t=1;
        int s=0;
        n=sc.nextInt();
        while (i<=n/2)
        {
            if (n%i==0) {
                t=0;
                s=s+i;
                System.out.println("Factors of "+ n +" are:");
                System.out.println("i= "+ i);
            }
            i++;
        }
//        System.out.println("n "+ (n));
        System.out.println("Sum of factors = "+ (s));
        if (t==1)
         System.out.println(n+" is prime");
        else
            System.out.println(n+" is composite");


    }
}