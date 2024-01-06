package for_loop;//package loop_progs;

import java.util.Scanner;

public class a_expo_n{
    public static void main(String[] args){
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter the value to calculate its exponent");
        int a;
        a=sc.nextInt();
        int n;
        System.out.println("Enter the value for exponent of "+ a);
        n=sc.nextInt();
        int expo=1;
        for (int i=1;i<=n;i++)
        {
           expo=expo*a;
        }
        System.out.println(a+"^"+n +" = "+ expo);
    }
}