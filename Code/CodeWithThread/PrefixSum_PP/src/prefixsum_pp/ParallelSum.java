/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prefixsum_pp;

/**
 *
 * @author mostafa
 */
public class ParallelSum {
    private ParallelWorker[] sums;
    private int numThreads;
    
    public ParallelSum(int numThreads){
        this.numThreads= numThreads;
        this.sums = new ParallelWorker[numThreads];
    }
    
    public int sum(int[] nums){
        int steps = (int) Math.ceil(nums.length / numThreads);
        
        
        for(int i=0;i<numThreads;i++){
            sums[i] = new ParallelWorker(nums, i*steps, (i+1) * steps);
            sums[i].start();
        }
        
        try{
            for(ParallelWorker pw : sums){
                pw.join();
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        
        int total = 0;
        
        for(ParallelWorker pw : sums){
            total += pw.getPartialSum();
        }
        return total;
        
    }
    
}
