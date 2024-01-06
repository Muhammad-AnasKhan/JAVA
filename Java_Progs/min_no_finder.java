public class min_no_finder {
  public static void main(String[] args) {
      int a,b,c,d,e,Min;
      a=49;
      b=59;
      c=79;
      d=999;
      e=9999;
      Min=a;

      if (Min > b)
          Min = b;

      if (Min > c)
          Min = c;

      if (Min > d)
          Min = d;

      if (Min > e)
          Min = e;
          System.out.println("Minimum Number= " + Min);
    }
  }
     