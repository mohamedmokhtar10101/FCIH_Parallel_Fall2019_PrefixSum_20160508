package prefixsum;

/* Recursive parallel prefix sums - with a constant number of threads */
import java.util.*;


class Adder extends Thread {
  int k;     /* thread id */
  int temp;

  int [][] y;
  int [][] z;

  Adder (int i, int [][] y, int [][] z) {
    k = i;
    this.y = y;
    this.z = z;
  }


  @Override
  public void run () {
    int l = PrefixSum.LOGSIZE;
    y[l][k] = PrefixSum.x[k];
    PrefixSum.barrier.allSync();
    
    prefix (l);
    
    PrefixSum.s[k] = z[l][k];
    PrefixSum.barrier.allSync();
  }


  void prefix (int l) {
    if (l == 0) { 
      if (k == 1) z[0][1] = y[0][1];
      PrefixSum.barrier.allSync();
      return;
    }
    
    if (k <= 1<<(l-1)) y[l-1][k] = y[l][2*k-1] + y[l][2*k];

    PrefixSum.barrier.allSync();

    prefix (l-1);

    if (k <= 1<<l) {
       if (k == 1) z[l][1] = y[l][1];
       else if (k%2 == 0) z[l][k] = z[l-1][k/2];
            else z[l][k] = z[l-1][(k-1)/2] + y[l][k];
      }

    PrefixSum.barrier.allSync();
 
  }

}

class MyInterruptXcp extends RuntimeException {
  MyInterruptXcp () { super (); }
}

class Barrier {
  final void mywait() {
    try { wait(); }
    catch (InterruptedException e) { throw new MyInterruptXcp(); }
   }
  private int totalNoThreads;
  private int noThreadsWaiting;

  Barrier (int n) {
    totalNoThreads = n;
    noThreadsWaiting = 0;
   }
  synchronized void allSync () {
    noThreadsWaiting ++;
    if (noThreadsWaiting < totalNoThreads) mywait();
    else {
      notifyAll();
      noThreadsWaiting = 0;
     }
   }
}


public class PrefixSum {

  public static int [] x;        /* input array  */
  public static int [] s;        /* output array */ 
  public static int LOGSIZE = 3;   /* default value for log of array size */
  public static int SIZE = 1 << LOGSIZE; 
  public static Barrier barrier = null ;

/* y and z act as shared arrays for all the threads :
   They are used to implement a shared stack for the recursive threads.
*/
  static int [][] y;
  static int [][] z;


  public static void serialPrefixSum(int  [] list)
  {
  for (int i = 1; i <= SIZE; i++) list[i] = list[i - 1] + list[i];  
  }
  public static void main (String[] args) throws InterruptedException {

/* read the size of the array from the command line */
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter  log size (you're gonna have to enter 2 ^ (logSize) elements ) :");
    LOGSIZE = sc.nextInt();   
    SIZE = 1 << LOGSIZE;

    y = new int [LOGSIZE + 1] [SIZE + 1];
    z = new int [LOGSIZE + 1] [SIZE + 1];

    x = new int[SIZE+1];            /* x[0] is not used */  
    System.out.println("Enter list elements you want to apply prefix sum on " + "( they are "+ SIZE + " elements ) :" );
    for (int i = 1; i <= SIZE; i++) x[i] = sc.nextInt();   
    
    s = new int[SIZE+1];
    barrier = new Barrier (SIZE) ;

    long t1 = new Date().getTime();   
    
/* launch threads */
    for (int i = SIZE; i>1; i--)
      new Adder(i, y, z).start();
    
    Adder last = new Adder(1, y, z);
    last.start();
    
/* wait for completion */
    last.join();
    System.out.println("Using PARALLEL PREFIX SUM the output list is : ");
    for (int i = 1; i <= SIZE; i++) System.out.print(s[i]+" ");   
    System.out.println("\nUsing Serial PREFIX SUM the output list is : ");
    serialPrefixSum(x);
    for (int i = 1; i <= SIZE; i++) System.out.print(x[i]+" ");   


    
    
   // long t2 = new Date().getTime();
  //  System.out.println ("Elapsed time: " + (t2 - t1) + " msec");

  }    

}


/* Results:
% java PrefixSum 5
Elapsed time: 87 msec
% java PrefixSum 6
Elapsed time: 244 msec
% java PrefixSum 7
Elapsed time: 761 msec
% java PrefixSum 8
Elapsed time: 2588 msec
% java PrefixSum 9
Elapsed time: 9542 msec
% java PrefixSum 10
Elapsed time: 38780 msec
% java PrefixSum 11
Elapsed time: 161856 msec
*/
