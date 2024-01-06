package Input_progs;

import java.util.*;
public class increment
{
    public static void main(String[] args)
    {
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter value of 'a' ");

        int a;
        a=sc.nextInt();
        int n;
        System.out.println("Enter value of 'n' ");
        n=sc.nextInt();
        while (a<=n){
            System.out.println( " i= "+ a++);

        }
    }
}