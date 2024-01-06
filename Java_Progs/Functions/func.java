package Functions;

public class func {
    public static void square(int n) 
    {
        System.out.println("square of "+ n +" = "+ (n*n)); 
    }
    public static void sum(int a,int b) 
    {
        System.out.println(a +" + "+ b +" = "+ (a+b)); 
    }
    public static int sum1(int a,int b) 
    {
        return(a+b); 
    }
    public static void test(int n) 
    {
        if (n%2==0)
        System.out.println(n+" is EVEN"); 
        else
        System.out.println(n+" is ODD"); 
    }
    
    public static void main(String[] args) {
    square(2); 
    sum(2,3);       
    sum(4,3);    
    test(8);   
    test(9); 
    int s=sum1(10,6);
    System.out.println("Sum = "+ s);
    int average=s/2;
    System.out.println("average = "+ average);
    }

}
