# Dynamic-Memory-Allocator
This repository contains the code of the implementation of a system which performs Dynamic Memory Allocation. This code is done as part of the Programming Assignment for the course- Data Structures and Algorithms taught in Indian Institute of Technology, Delhi, India.
### What is Memory Allocation?
It is the reservation of portions of the Computer memory for execution of processes. Thus, this system will be running on our machines and it will give out memories to the programs as requested by them.
There are two types of Memory Allocation-
1. Static Memory Allocation
2. Dynamic Memory Alloction
### About the System-
This System for Memory Allocation will be having an information about the memory spaces that would be currently occupied by other programs and the memory spaces that are currently available, so that whenever a program asks for memory only the memory block that is marked free (or currently available) will be given to that program. Now whenever a free memory block is given to a program then it is marked as occupied (or allocated) so other programs can not access it.

Now it even happens that the program that was using this memory block realizes that it has no more work with this memory block and so it decides to release this block, enabling other programs to be able to use this block. Thus, a program can even mark a memory block as free, which then becomes available for the other programs as well.

#### How does the system decide which block to give if there are many free blocks available? 
There is a bunch of algorithms and strategies available in the literature, but only the following two algorithms are implemented:

##### 1. First Fit Algorithm:
Here the Allocation system will go through all the Free Memory blocks, stored using any Data structure and first block that is big enough to fulfill the needs of the program will be returned. The process of finding the first such block will finish quickly in general (so it is good in time), but there will be an issue regarding the loss of memory (so it is bad in space utilisation). 

##### 2. Best Fit Algorithm: 
Rather than finding the first adequate memory block, the system will go through all the free blocks and will return the smallest block which will be big enough to fulfill the needs of the program. Here as it will need to go through the entire data structure of free blocks so it will be slower (so it is bad in time). But there will be a minimum loss of memory here, as it is finding the best block possible to be assigned (so it is good in space utilisation). 

Thus from the above two algorithms, you can see the trade-off between having the fastest algorithm for allocation and having the most eficient space utilisation algorithm for allocation. Note that along with these two criterion, another criterion which is extremely important for allocation of memory is that the free blocks that are contiguous should be merged into one larger free block.

It is assumed that the memory of the system to be an array of size M (bytes), which lies between 10 million to 100 million. Each element of this array would be addressed using its index. We take the size of one memory address as 1 Byte. The Memory Allocation System will be maintaining two data structures, one for the free blocks and one for the allocated blocks. So initially, the data structure for allocated blocks will be empty and the data structure for the free blocks will be having just one element which is the entire memory. Here, the system will be allocating memory using a variant of First Fit and Best Fit algorithm. These variants will be called First Split Fit and Best Split Fit algorithm.

The main functionality of the Memory Allocator will be To allocate free memory and To free allocated memory. In order to solve the issue of Fragmentation, the system will also Defragment the free memory. It basically searches for consecutive free blocks and merges them into one bigger block.
An abstract data type called Dictionary is used to store the free memory blocks and allocated memory blocks. This is implemented using 3 Data Structures-
1. Doubly Linked List
2. Binary Search Tree
3. AVL Tree

