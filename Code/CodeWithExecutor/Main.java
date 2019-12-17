import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

public class Main{

  public static void main(String[] args) throws Exception{
      long [] listOfNumbers = new  long [8];
      for(int i=0;i<8;++i){
        listOfNumbers[i] = i;
      }
      int listOfNumbersSize = listOfNumbers.length;
      int stagesSize = (int)(Math.ceil( Math.log(listOfNumbersSize) / Math.log(2)))+1;
      long result = 0;
      long [][] stages = new long [stagesSize][listOfNumbersSize];
      initializeFirstStage(stages, listOfNumbers, listOfNumbersSize);
      System.out.println("Hello");

      // Serial
      long startTime1 = System.nanoTime();
      result = calculatePrefixSumSerial(listOfNumbers);
      long endTime1 = System.nanoTime();
      System.out.println("The Serial prefix sum of the list is: " + result);
      long durationNano1 = (endTime1 - startTime1);
      long durationMillis1 = TimeUnit.NANOSECONDS.toMillis(durationNano1);
      System.out.println("\nSerial: "+ durationMillis1);

      // // Parallel
      long startTime = System.nanoTime();
      result = calculatePrefixSumParallel(stages, listOfNumbers, stagesSize, listOfNumbersSize);
      long endTime = System.nanoTime();
      System.out.println("The parallel Prefix sum of the list is: " + result);
      long durationNano = (endTime - startTime);
      long durationMillis = TimeUnit.NANOSECONDS.toMillis(durationNano);
      System.out.println("\nParallel: "+ durationMillis);
    }

  public static long calculatePrefixSumSerial(long[] listOfNumbers){
    long  currentPrefixSum = 0;
    for(long number : listOfNumbers){
      currentPrefixSum+=number;
    }
    return currentPrefixSum;
  }

  public static long calculatePrefixSumParallel(long [][] stages, long [] listOfNumbers, int stagesSize, int listOfNumbersSize) throws Exception{
    ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool((int)Math.ceil(0.6*Runtime.getRuntime().availableProcessors()));
    for(int i =0;i<stagesSize-1;++i){
      CountDownLatch controller = new CountDownLatch(listOfNumbersSize);
      // for(int j=0;j < listOfNumbersSize;++j){
      //   PrefixSumParallel parallelSum = new PrefixSumParallel(stages, i, j, controller);
      //   executor.execute(parallelSum);
      // }
      PrefixSumParallel parallelSum = new PrefixSumParallel(stages, i, listOfNumbersSize, controller);
      executor.execute(parallelSum);
      controller.await();
    }
    executor.shutdown();
    // for(int i=0;i<stagesSize;++i){
    //   for(int j=0;j<listOfNumbersSize;++j){
    //     System.out.println("Stage: " + stages[i][j]);
    //   }
    // }
    System.out.println(stages[stagesSize-1][listOfNumbersSize-1]);
    long s = stages[stagesSize-1][listOfNumbersSize-1];
    return stages[stagesSize-1][listOfNumbersSize-1];
  }

  public static void initializeFirstStage(long [][] stages, long [] listOfNumbers, int listOfNumbersSize){
    for(int i=0;i<listOfNumbersSize;++i){
      stages[0][i] = listOfNumbers[i];
    }
  }



}
