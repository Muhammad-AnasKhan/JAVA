package Input_progs;

import java.util.*;
public class Table
{
    public static void main(String[] args)
    {
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter a number to write its table ");

        int t;
        t=sc.nextInt();

        System.out.println("Enter a range  to write the table of "+t);

        int start=1;
        int range;
        range=sc.nextInt();

        while (start<=range){
            System.out.println(t+ " * " +start+ " = "+ (t*start) );
            start++;
        }
    }
}
