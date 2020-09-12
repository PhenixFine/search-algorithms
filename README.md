# search-algorithms
A side project I'm working on as I learn new algorithms. It currently only has Jump and Binary search. A jump and binary search time complexity is O(√n) and O(log n), respectively. For a search of 100,000 numbers, binary takes an average of 15 steps, while jump takes an average of 173 steps.

## Notes:
### Test.kt
I created it to make use of any search algorithms that I create, and to show the number and average of steps it takes different algorithms to do a search of an Int array. It can also create a random array of numbers, store the numbers in a file, and retrieve the numbers.

### JumpSearch.kt
 I based it on the Java versions of https://www.geeksforgeeks.org/jump-search/ ( their version is broken, and takes some unnecessary steps, even when fixed ) and https://hyperskill.org/learn/step/3548 which I found after trying to find a working version ( there's a lot of broken versions seemingly based on the geeksforgeeks.org version ). I took what I liked from each version and then adjusted it to keep making jumps for as long as it was practical to do so ( it stops making jumps once the number of jumps falls below 2, and then switches to either linear forward or linear backwards ).

### BinarySearch.kt
I based it on BinarySearchRecursive.kt in the old package ( which is based on https://www.geeksforgeeks.org/binary-search/ Java version ). I read that recursion can take up more memory, and that CPU's have built in support for loops like the while loop used in this version. So I thought I'd switch it from a recursive function to a while loop function that loops through another function.
