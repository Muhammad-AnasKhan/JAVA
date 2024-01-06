package loop_progs;
public class factorial_of_5
{
    public static void main(String[] args)
    {
        int p=1;
        int i=1;
        while(i<=5){
            p=p*i;
            i++;
        }
        System.out.println("Factorial of '5' = "+ p);
    }
}