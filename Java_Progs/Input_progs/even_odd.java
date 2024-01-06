package Input_progs;

import java.util.*;
public class even_odd
{
    public static void main(String[] args)
    {
        Scanner sc =new Scanner(System.in);
        int i=1;
        int n;
        System.out.println("Enter value of 'n' ");
        n=sc.nextInt();
        while (i<=n){
            if(i%2==0)
            System.out.println( " EVEN= "+ i++);
            else
                System.out.println( " ODD= "+ i++);

        }
    }
}