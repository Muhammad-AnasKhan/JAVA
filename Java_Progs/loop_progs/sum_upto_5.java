package loop_progs;
public class sum_upto_5
{
    public static void main(String[] args)
    {
        int i,s;
        i=1;
        s=0;
        while(i<=5){
            s=s+i;
            i++;
        }
        System.out.println("Sum upto 5 = "+s);

    }
}