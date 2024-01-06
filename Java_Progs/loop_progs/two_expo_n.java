package loop_progs;

import java.util.Scanner;

public class two_expo_n{
    public static void main(String[] args){
        Scanner sc =new Scanner(System.in);
        int n;
        int i=1;
        int p=1;
        System.out.println("Enter the value for exponent of 2 ");
        n=sc.nextInt();

        while(i<=n){
            p=p*2;
            i++;
        }
        System.out.println("2^"+n +" = "+ p);
    }
}