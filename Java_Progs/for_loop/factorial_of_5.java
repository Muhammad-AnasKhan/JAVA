package for_loop;

//package loop_progs;
public class factorial_of_5
{
    public static void main(String[] args)
    {
        int fac=1;
        for(int i=1;i<=5;i++){
            fac=fac*i;
            i++;
        }
        System.out.println("Factorial of '5' = "+ fac);
    }
}