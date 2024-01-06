package loop_progs;
public class factorial_of_10
{
    public static void main(String[] args)
    {
        int p=1;
        int i=1;
        while(i<=10){
            p=p*i;
            i++;
        }
        System.out.println("Factorial of '10' = "+ p);
    }
}