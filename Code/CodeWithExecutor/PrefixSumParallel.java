import java.util.concurrent.CountDownLatch;
import java.lang.Math;

public class PrefixSumParallel implements Runnable{
  private final long[][] stages;
  private final int stage;
  private final int index;
  private final CountDownLatch controller;

  public PrefixSumParallel(long[][] stages, int stage, int index, CountDownLatch controller){
    this.stages = stages;
    this.stage = stage;
    this.index = index;
    this.controller = controller;
  }

  @Override
  public void run(){
    for(int j=0;j < index;++j){
      if(j < Math.pow(2, stage)){
        stages[stage+1][j] = stages[stage][j];
      }else{
        stages[stage+1][j] = stages[stage][j] + stages[stage][j - (int)Math.pow(2, stage)];
      }
      controller.countDown();
    }
    // try{
    //   if(index < Math.pow(2, stage)){
    //     stages[stage+1][index] = stages[stage][index];
    //   }else{
    //     stages[stage+1][index] = stages[stage][index] + stages[stage][index - (int)Math.pow(2, stage)];
    //   }
    //   controller.countDown();
    // }catch(Exception e){
    //   e.printStackTrace();
    // }
  }
}
