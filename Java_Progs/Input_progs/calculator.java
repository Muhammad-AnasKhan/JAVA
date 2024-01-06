package Input_progs;

import java.util.*;
public class calculator {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        System.out.println("Input 1st value");
        int x,y;
        x=sc.nextInt();
        System.out.println("Input 2nd value");
        y=sc.nextInt();
        System.out.println();
        System.out.println("Sum of " + x + " and " + y + " is " + (x + y));
        System.out.println("Difference of " + x + " and " + y + " is " + (x - y));
        System.out.println("Product of " + x + " and " + y + " is " + (x * y));
        System.out.println("Quotient of " + x + " and " + y + " is " + (x / y));
        System.out.println("Remainder of " + x + " and " + y + "  is " + (x % y));

    }
}

