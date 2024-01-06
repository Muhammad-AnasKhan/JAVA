public class swap2 {
  public static void main(String[] args) {
    // swapping var values without using 3rd variable
    System.out.println("swapping values without using 3rd variable");
    System.out.println(" ");
    int a = 10;
    int b = 20;
    System.out.println("Values before swap");
    System.out.println("a= " + a);
    System.out.println("b= " + b);

    a = a + b; // a=30
    b = a - b; // b=10
    a = b + b; // a=20
    System.out.println("Values After swap");
    System.out.println("a= " + a);
    System.out.print("b= " + b);

    





  }
}
