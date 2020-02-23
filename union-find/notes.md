# notes

### Algorithms improving

| algorithm                                  | connected | union     | worst-case time |
| :----------------------------------------- | --------- | --------- | --------------- |
| quick-find                                 | O(1)      | O(n)      | O(M N)          |
| quick-union                                | O(n)      | O(n)      | O(M N)          |
| weighted quick-union                       | O(log n)  | O(log n)  | O(N + M log N)  |
| weighted quick-union with path compression | O(log* n) | O(log* n) | O(N + M log* N) |



### Exercise percolation is really hard

![image-20200219145416624](image-20200219145416624.png)



### Application

- Percolation. 
- Games (Go, Hex). 
- Dynamic connectivity.
- Least common ancestor. 
- Equivalence of finite state automata.
- Hoshen-Kopelman algorithm in physics. 
- Hinley-Milner polymorphic type inference. 
- Kruskal's minimum spanning tree algorithm. 
- Compiling equivalence statements in Fortran. 
- Morphological attribute openings and closings. 
- Matlab's bwlabel() function in image processing.



### Mathematical property

- Reflexive
- Symmetric
- Transitive



### Steps to developing a usable algorithm. 

- Model the problem. 
- Find an algorithm to solve it. 
- Fast enough? Fits in memory? 
- If not, figure out why. 
- Find a way to address the problem. 
- Iterate until satisfied.