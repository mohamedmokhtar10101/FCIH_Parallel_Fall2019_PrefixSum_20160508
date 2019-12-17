/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prefixsum_pp;

import java.util.Random;

/**
 *
 * @author mostafa
 */
public class PrefixSum_PP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random rand = new Random();
        int[] nums = new int[100000000];
        for(int i=0;i<nums.length;i++){
            nums[i] = rand.nextInt(100);
        }
        /**
         * Serial/Sequential Sum
         */
        SerialSum serialSum = new SerialSum(nums);
        long serialSumStart = System.currentTimeMillis();
        System.out.println("Serial Sum: "+ serialSum.getTotalSum());
        System.out.println("Serial Time: " + (System.currentTimeMillis() - serialSumStart ) + "ms");
        
        /**
         * Parallel Sum
         */
        ParallelSum parallelSum = new ParallelSum(8);
        long parallelSumStart = System.currentTimeMillis();
        System.out.println("Parallel Sum: " + parallelSum.sum(nums));
        System.out.println("Parallel Time: " + (System.currentTimeMillis() - parallelSumStart ) + "ms");
        
    }
    
}
