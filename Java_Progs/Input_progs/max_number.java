package Input_progs;
import java.util.*;
public class max_number {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        int a,b,c,d,e,Max;
        System.out.println("Enter 1st Number ");
        a=sc.nextInt();
        System.out.println("Enter 2nd Number ");
        b=sc.nextInt();
        System.out.println("Enter 3rd Number ");
        c=sc.nextInt();
        System.out.println("Enter 4th Number ");
        d=sc.nextInt();
        System.out.println("Enter 5th Number ");
        e=sc.nextInt();

        Max=a;

        if (Max < b)
            Max = b;

        if (Max < c)
            Max = c;

        if (Max < d)
            Max = d;

        if (Max < e)
            Max = e;
        System.out.println("The Greatest Number= " + Max);
    }
}
