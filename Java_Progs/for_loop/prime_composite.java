package for_loop;//package loop_progs;

import java.util.Scanner;

public class prime_composite{
    public static void main(String[] args){
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter a number to check whether it is prime or composite");
        int s=0;
        int prime=1;
        int n;
        n=sc.nextInt();
        for (int i=2;i<=(n/2);i++)
        {
            if (n%i==0) {
                prime = 0;
                s=s+i;
                System.out.println("i= "+ i);
            }
        }
//        System.out.println("n "+ (n));
        System.out.println("Sum of factors = "+ (s));
        if (prime==0) {
            System.out.println(n+" is composite");
        }
        else {
            System.out.println(n+" is prime");
        }

    }
}