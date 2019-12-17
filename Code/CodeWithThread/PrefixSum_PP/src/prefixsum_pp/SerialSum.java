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
public class SerialSum {
    int[] nums;
    public SerialSum(int[] nums){
        this.nums = nums;
    }
    
    public int getTotalSum(){
        int total = 0;
        for(int i=0;i<nums.length;i++){
            total += nums[i];
        }
        return total;
    }
    
}
