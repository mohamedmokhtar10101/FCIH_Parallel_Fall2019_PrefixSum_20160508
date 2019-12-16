# Serial and parallel prefix sum computation


### Team Names and IDs

| ID | Name |
| ------ | ------ |
| 20160508 | يوسف حاتم عبد الرشيد جلال |
| 20120599 | محمد مختار حسانين محمد |
| 20150533 | مصطفي صالح صبيح محمد |
| 20160429 | مصطفى محسن مصطفى |


## Algorithms used :
- ## The two approaches to the problem are : 
  * #### Serial.
  * #### Parallel.
#### Serial Algorithm:
>We simply loop over all the elements in the input array and add the value of the previous element of the input array to the sum computed for the previous element of the output array, and write the sum to the current element of the output array. 
```sh
out[0] := 0
for k := 1 to n do
  out[k] := in[k-1] + out[k-1]
```
