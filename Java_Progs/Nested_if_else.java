public class Nested_if_else {
  public static void main(String[] args) {
//program to find max number
      int a,b,c;
      a=100;
      b=1000;
      c=1990;
      if (a>b)
          if (a>c)
              System.out.println("a="+ a + " is greatest");
          else
              System.out.println("b="+ b +  " is greatest");
      else
          if (b>c)
              System.out.println("b="+ b +  "  greatest");
          else
              System.out.println("c="+ c +  " is greatest");


    }
  }
     