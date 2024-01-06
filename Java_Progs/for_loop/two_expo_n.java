package for_loop;//package loop_progs;

import java.util.Scanner;

public class two_expo_n{
    public static void main(String[] args){
        Scanner sc =new Scanner(System.in);
        int n;
        int expo = 1;
        System.out.println("Enter the value for exponent of 2 ");
        n=sc.nextInt();
        for (int i=1;i<=n;i++)
        {
            expo=expo*2;
        }
        System.out.println("2^"+n+" = "+expo);
    }
}