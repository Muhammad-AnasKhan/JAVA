package for_loop;//package loop_progs;

import java.util.Scanner;
public class fib{
    public static void main(String[] args){
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter a number to to print fibonacci series upto it ");
        int n;
        n=sc.nextInt();
//        int a0=0;
        int a1=1;
        int sum=0;

        for (int a0=0;a0<=n;)
        {
            System.out.println(a0);
            sum=a0+a1;
            a0=a1;
            a1=sum;
        }

    }
}