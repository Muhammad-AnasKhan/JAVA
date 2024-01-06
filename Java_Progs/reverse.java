public class reverse {
  public static void main(String[] args) {
      int n,x,y,r;
      n=892;
      x=n/100; //8
      n=n%100; //92
      y=n/10;  //9
      n=n%10;  //2

      System.out.println("Sum of Numbers= "+(x+y+n));
      r=(n*100)+(y*10)+(x);
      System.out.print("Reverse= "+r);
      System.out.println(" ");
    }
  }
     