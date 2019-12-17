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
public class ParallelWorker extends Thread{
    private int[] nums;
    private int low;
    private int high;
    private int partialSum;
    
    public ParallelWorker(int[] nums, int low, int high){
        this.nums = nums;
        this.low = low;
        this.high = high;
    }
    
    public int getPartialSum(){
        return this.partialSum;
    }
    
    @Override
    public void run(){
        partialSum = 0;
        for(int i=low;i<high;i++){
            partialSum += nums[i];
        }
    }
    
    
}
