// package do_loop_progs;

import java.util.Scanner;

public class a_expo_n{
    public static void main(String[] args){
        Scanner sc =new Scanner(System.in);
        int n;
        int i=1;
        int p=1;
        int a;
        System.out.println("Enter the value to calculate its exponent");
        a=sc.nextInt();

        System.out.println("Enter the value for exponent of "+ a);
        n=sc.nextInt();

        do {
            p=p*a;
            i++;
        }while(i<=n);
        System.out.println(a+"^"+n +" = "+ p);
    }
}