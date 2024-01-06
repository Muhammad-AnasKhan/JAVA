package array;
import java.util.Scanner;
public class array {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter the number of integers you want to add");
        int num =sc.nextInt();
        int a[] = new int[num];
        System.out.println("Enter "+num+" numbers");
//        int total=0;
        int f;
        int user_input;
         user_input =sc.nextInt();

         f=0;
        for (int i=0;i<a.length;i++){
            a[i] =sc.nextInt();
        }
        for (int i=0;i<a.length;i++){
            if (user_input==a[i])
                f=1;

        }

        if (f==1)
            System.out.println("Number found ");
        else
            System.out.println("Number found ");


//        System.out.println("sum= "+total);
//         int max=0;
//         for (int i=0;i<a.length;i++){
//             if (a[i] > max)
//                 max = a[i];
//         }
//         System.out.println("Max is " + max);



//            total+=a[i];
    }
}








//package array;
//        import java.util.Scanner;
//public class array {
//    public static void main(String[] args) {
//        Scanner sc =new Scanner(System.in);
////        System.out.println("Enter the number of integers you want to add");
////        int num =sc.nextInt();
////        int a[] = {num};
//        int a[] = {};
////        int total=0;
////        System.out.println("Enter the numbers to add in array");
////        for (int i=0;i<a.length;i++){
////            a[i] =sc.nextInt();
////        }
//        int f;
//        int user_input;
//        System.out.println("Enter the number you want to search");
//        user_input =sc.nextInt();
//
//        f=0;
//        for (int i=0;i<a.length;i++){
//            if (user_input==a[i])
//                f=1;
//        }
//        if (f==1)
//            System.out.println("Number found ");
//        else
//            System.out.println("Number not found ");
//
//
////        System.out.println("sum= "+total);
////         int max=0;
////         for (int i=0;i<a.length;i++){
////             if (a[i] > max)
////                 max = a[i];
////         }
////         System.out.println("Max is " + max);
//
//
//
////            total+=a[i];
//    }
//}


















////package array;
////        import java.util.Scanner;
//public class array {
//    public static void main(String[] args) {
//        Scanner sc =new Scanner(System.in);
//        int a[] = {1,3,5,7,9};
//        int f;
//        int user_input;
//        System.out.println("Enter the number you want to search");
//        user_input =sc.nextInt();
//
//        f=0;
//        for (int i=0;i<a.length;i++){
//            if (user_input==a[i])
//                f=1;
//        }
//        if (f==1)
//            System.out.println("Number found ");
//        else
//            System.out.println("Number not found ");
//
//
////        System.out.println("sum= "+total);
////         int max=0;
////         for (int i=0;i<a.length;i++){
////             if (a[i] > max)
////                 max = a[i];
////         }
////         System.out.println("Max is " + max);
//
//
//
////            total+=a[i];
//    }
//}