/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prefix_sum;

import java.util.Date;
import java.util.Scanner;
import java.lang.Math; 

/**
 *
 * @author AliMohammed
 */
public class PrefixSum {

  public static int [] inputArr;        /* input array  */
  public static int [] outputArr;        /* output array */ 
  public static int LOGSIZE = 3;   /* default value for log of array size */
  //
  public static int n = (int) Math.pow(2, LOGSIZE); 
  
  public static Barrier barrier = null ;

/* y and z act as shared arrays for all the threads :
   They are used to implement a shared stack for the recursive threads.
*/
  static int [][] y;
  static int [][] z;


 
  public static void main (String[] args) throws InterruptedException {

/* read the size of the array from the command line */
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter LOGSIZE to enter n  elements 2 ^ LOGSIZE:");
    LOGSIZE = sc.nextInt();
    n = (int) Math.pow(2, LOGSIZE);
    
    y = new int [LOGSIZE + 1] [n + 1];
    z = new int [LOGSIZE + 1] [n + 1];

    inputArr = new int[n+1];            // start from inputArr[1]  
    System.out.println("Enter input array elements " +   n + " elements ) :" );
    for (int i = 1; i <= n; i++)
        inputArr[i] = sc.nextInt();   
    
    outputArr = new int[n+1];
    barrier = new Barrier (n) ;

    long t1 = new Date().getTime();   
    
/* launch threads */
    for (int i = n; i>1; i--)
      new Summition(i, y, z).start();
    
    Summition last = new Summition(1, y, z);
    last.start();
   
    
/* wait for completion */
    last.join();
    System.out.println("parallel Prefix Sum is  : ");
    for (int i = 1; i <= n; i++) System.out.print(outputArr[i]+" ");  
      
    
    long t2 = new Date().getTime();
    System.out.println (" time: " + (t2 - t1) + " msec");

  }    

}
