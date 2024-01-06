//package loop_progs;
public class sum_upto_10
{
    public static void main(String[] args)
    {
        int i,s;
        i=1;
        s=0;
        do {
            s=s+i;
            i++;
        }while(i<=10);
        System.out.println("Sum upto 10= "+s);

    }
}