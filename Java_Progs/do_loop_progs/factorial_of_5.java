//package loop_progs;
public class factorial_of_5
{
    public static void main(String[] args)
    {
        int p=1;
        int i=1;
        do {
            p=p*i;
            i++;
        }while(i<=5);
        System.out.println("Factorial of '5' = "+ p);
    }
}