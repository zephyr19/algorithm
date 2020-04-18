# Algorithms

### What?

|      topic | data structures and algorithms                    |
| ---------: | :------------------------------------------------ |
| data types | stack, queue, priority queue, bag, union-find     |
|    sorting | quicksort, mergesort, heapsort                    |
|  searching | BST, red-black BST, hash table                    |
|     graphs | BFS, DFS, Prim, Kruskal, Dijkstra                 |
|    strings | radix sort, tries, KMP, regexps, data compression |
|   advanced | B-tree, suffix array, maxflow                     |

### **Why?**

**algorithms' impact is broad and far-reaching**

applied in many fields: bio, security, multimedia, network, graph 

**Internet:** Web search, packet routing, distributed file sharing

**Biology:** Human genome project, protein folding

**Computers:** Circuit layout, file system, compilers

**Computer graphics:** Movies, video games, virtual reality

**Security:** Cell phones, e-commerce, voting machines

**Multimedia**: MP3, JPG, DivX, HDTV, face recognition

**Social networks**: Recommendations, news feeds, advertisements

**Physics:** N-body simulation, particle collision simulation



# AlgorithmsPartI-Princeton

Code for programming assignments from Algorithms Part I (https://class.coursera.org/algs4partI-004).

Requires the stdlib.jar and algs4.jar to run, which can be retrieved from the course site.

## Week 1 - UnionFind / Percolation

- **Percolation.java** - A model for the percolation problem, determines if a 2d system of open / closed sites percolates from top to bottom.
- **PercolationStats.java** - Generates statistics using the percolation model.

## Week 2 - Queues, Stacks and Bags

- **Deque.java** - A generic double ended queue implementation, double-linked list based.
- **RandomizedQueue.java** - A generic random queue implementation, array based.
- **Subset.java** - Prints n number of random strings provided through standard input.

## Week 3 - Collinear Points

- **Point.java** - A simple point class.
- **Brute.java** - A n^4 algorithm for calculating 4 collinear points.
- **Fast.java** - A fast implementation for finding collinear points.

## Week 4 - 8 Puzzle

- **Board.java** - Represents a sliding puzzle board.
- **Solver.java** - Uses A* and to find solution to the puzzle board.

## Week 5 - KdTree

- **PointSET.java** - A set of points on a 2D Euclidian plane, some simple function like nearest neighbor search and range search using a brute force approach.
- **KdTree.java** - Uses a 2d tree to more efficiently perform functions such as nearest neighbor search and range search.



# Algorithms PartII-Princeton

Code for programming assignments from Algorithms Part II (https://class.coursera.org/algs4partII-003).

Requires the stdlib.jar and algs4.jar to run.

## Week 1 - WordNet

- **WordNet.java** - A WordNet represented as a directed graph.
- **SAP.java** - Represents an immutable data type for shortest ancestral paths.
- **Outcast.java** - Identifies outcasts.

## Week 2 - Seam Carving (Content-Aware Resizing)

- **SeamCarver.java** - Seam-carving is a content-aware image resizing technique where the image is reduced in size by one pixel of height (or width) at a time.

## Week 3 - Baseball Elimination

- **BaseballElimination.java** - We use maximum flow algorithms to solve the baseball elimination problem.

## Week 4 - Boggle

Boggle is a word game designed by Allan Turoff and distributed by Hasbro. It involves a board made up of 16 cubic dice, where each die has a letter printed on each of its sides. At the beginning of the game, the 16 dice are shaken and randomly distributed into a 4-by-4 tray, with only the top sides of the dice visible.

- **BoggleBoard.java** - Represents a Boggle board.
- **BoggleSolver.java** - Implements an algorithm to solve Boggle boards.

## Week 5 - Burrows-Wheeler Compression Algorithm

Implement the Burrows-Wheeler data compression algorithm. This revolutionary algorithm outcompresses gzip and PKZIP, is relatively easy to implement, and is not protected by any patents. It forms the basis of the Unix compression utililty bzip2.

- **CircularSuffixArray.java** - Describes the abstraction of a sorted array of the N circular suffixes of a string of length N.
- **MoveToFront.java** - Maintains an ordered sequence of the 256 extended ASCII characters and provides encoding and decoding steps.
- **BurrowsWheeler.java** - Implements the actual encoding and decoding algorithms.