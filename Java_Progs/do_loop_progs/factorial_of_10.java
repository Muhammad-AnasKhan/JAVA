//package loop_progs;
public class factorial_of_10
{
    public static void main(String[] args)
    {
        int p=1;
        int i=1;
        do {
            p=p*i;
            i++;
        }while(i<=10);
        System.out.println("Factorial of '10' = "+ p);
    }
}