package loop_progs;

import java.util.Scanner;

public class two_expo_5{
    public static void main(String[] args){
        int i=1;
        int expo=1;

        while(i<=5){
            expo=expo*2;
            i++;
        }
        System.out.println("2^5 = "+ expo);
    }
}