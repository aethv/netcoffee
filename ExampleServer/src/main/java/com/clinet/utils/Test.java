package com.clinet.utils;



public class Test {
    
	public static void main (String[] args) {
        int i = 1;
        System.out.println(~i);
        System.out.println("i: " + i);
        i &= 0;
        System.out.println("i: " + i);
        i &= ~0;
        System.out.println("i: " + i);
        i &= 1;
        System.out.println("i: " + i);
        i &= ~1;
        System.out.println("i: " + i);
        
        System.out.println("-----------------");
        
        i = 0;
        System.out.println(~i);
        System.out.println("i: " + i);
        i &= 0;
        System.out.println("i: " + i);
        i &= ~0;
        System.out.println("i: " + i);
        i &= 1;
        System.out.println("i: " + i);
        i &= ~1;
        System.out.println("i: " + i);
        
    }
}
