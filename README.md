# Serial and parallel prefix sum computation


### Team Names and IDs

| ID | Name |
| ------ | ------ |
| 20160508 | يوسف حاتم عبد الرشيد جلال |
| 20120599 | محمد مختار حسانين محمد |
| 20150533 | مصطفي صالح صبيح محمد |
| 20160429 | مصطفى محسن مصطفى |
| 20160331 | محمد أحمد محمد سليم |


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
#### Parallel Algorithm:
> The Algorithm is simply divided into **Stages** , ***which by the way it's size is: log2(N) : N => Size of the Array***, The first **Stage** always contain the actual array that is going to be summed.\
To get each ***element*** in the other **Stages**, you have to sum (the current ***element*** from the previous **Stage**, which it's index is the same as the current index) by (the ***element*** from the previous **Stage**, which it's index is 2-(the current index)^2).\
**IF** the index of ***element*** is *less* than (2 - (the current index)^2), it will equal the ***Previous element*** from the ***Previous Stage*** with the same **Index**.
```sh
if current_index < 2^(current_stage_index) then
    Element(current_index)(current_stage_index+1) := Element(current_index)(current_stage_index)
else
    Element(current_index)(current_stage_index+1) := Element(current_index)(current_stage_index) + Element(current_index - 2^(current_stage_index))(current_stage_index)
```
