//package loop_progs;

import java.util.Scanner;

public class fib{
    public static void main(String[] args){
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter a number to to print fibonacci series upto it ");
        int n;
        int a=0,b=1;
        int s=0;
        n=sc.nextInt();

        do {
            System.out.println(a);
            s=a+b;
            a=b;
            b=s;
        }while (a<=n);



    }
}