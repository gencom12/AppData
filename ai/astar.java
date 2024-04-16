import java.util.*;

class astar
 {
    // Size of the puzzle board
    public static int N1 = 3;

    // Node class represents a state in the search space
    public static class Node {
        Node parent1;  // storing the parent node of the current node
        int m[][] = new int[N1][N1];  // storing the matrix
        int x1, y1;  // storing the blank tile coordinates
        int cost1;   // storing the number of misplaced tiles
        int level1;  // storing the number of moves so far
    }

    // Method for printing N1 x N1 matrix
    public static void printMatrix(int m[][]) {
        for (int i1 = 0; i1 < N1; i1++) {
            for (int j1 = 0; j1 < N1; j1++) {
                System.out.print(m[i1][j1] + " ");
            }
            System.out.println("");
        }
    }

    // Method for allocating a new node
    public static Node newNode(int m[][], int x1, int y1, int newX1, int newY1, int level1, Node parent1) {
        Node node1 = new Node();
        node1.parent1 = parent1;

        node1.m = new int[N1][N1];
        for (int i1 = 0; i1 < N1; i1++) {
            for (int j1 = 0; j1 < N1; j1++) {
                node1.m[i1][j1] = m[i1][j1];
            }
        }

        int temp1 = node1.m[x1][y1];
        node1.m[x1][y1] = node1.m[newX1][newY1];
        node1.m[newX1][newY1] = temp1;

        node1.cost1 = Integer.MAX_VALUE;
        node1.level1 = level1;

        node1.x1 = newX1;
        node1.y1 = newY1;

        return node1;
    }

    // Directions for moving tiles (bottom, left, top, right)
    public static int row1[] = {1, 0, -1, 0};
    public static int col1[] = {0, -1, 0, 1};

    // Method for calculating the number of misplaced tiles
    public static int calculateCost(int initialM[][], int finalM[][]) {
        int count1 = 0;
        for (int i1 = 0; i1 < N1; i1++)
            for (int j1 = 0; j1 < N1; j1++)
                if (initialM[i1][j1] != 0 && initialM[i1][j1] != finalM[i1][j1])
                    count1++;
        return count1;
    }

    // Method for checking if (x1, y1) is a valid matrix coordinate
    public static int isSafe(int x1, int y1) {
        return (x1 >= 0 && x1 < N1 && y1 >= 0 && y1 < N1) ? 1 : 0;
    }

    // Printing path from a root node to the destination node
    public static void printPath(Node root1) {
        if (root1 == null) {
            return;
        }
        printPath(root1.parent1);
        printMatrix(root1.m);
        System.out.println("");
    }

    // Comparing instances to be used to order the heap
    public static class comp implements Comparator<Node> {
        @Override
        public int compare(Node lhs1, Node rhs1) {
            return (lhs1.cost1 + lhs1.level1) > (rhs1.cost1 + rhs1.level1) ? 1 : -1;
        }
    }

    // Method for solving N1*N1 - 1 puzzle algorithm using Branch and Bound method
    public static void solve(int initialM[][], int x1, int y1, int finalM[][]) {
        // Creating a priority queue for storing the live nodes of the search tree
        PriorityQueue<Node> pq1 = new PriorityQueue<>(new comp());

        // Creating a root node and calculating its cost
        Node root1 = newNode(initialM, x1, y1, x1, y1, 0, null);
        root1.cost1 = calculateCost(initialM, finalM);

        // Adding root1 to the list of the live nodes
        pq1.add(root1);

        // Finding a live node with the least cost, adding its children to the list of live nodes,
        // and finally deleting it from the list
        while (!pq1.isEmpty()) {
            Node min1 = pq1.peek();
            pq1.poll();

            if (min1.cost1 == 0) {
                // If minimum is an answer node, print the path from the root to the destination
                printPath(min1);
                return;
            }

            // Generating children for the current node and adding them to the list of live nodes
            for (int i1 = 0; i1 < 4; i1++) {
                if (isSafe(min1.x1 + row1[i1], min1.y1 + col1[i1]) > 0) {
                    Node child1 = newNode(min1.m, min1 .x1, min1.y1, min1.x1 + row1[i1], min1.y1 + col1[i1], min1.level1 + 1, min1);
                    child1.cost1 = calculateCost(child1.m, finalM);
                    pq1.add(child1);
                }
            }
        }
    }

    // Main code
    public static void main(String args[]) {
        // First configuration (Value 0 is used for the null space)
        int initialM[][] = {
            {3,8,2},
            {4,6,1},
            {5,7,0}
        };

        // Solvable last configuration (Value 0 is used for the null space)
        int finalM[][] = {
              {3,2,1},
            {4,0,8},
            {5,6,7}
        };

        // Blank tile coordinates in the first configuration
        int x1 = 2, y1 = 2;

        // Solve the puzzle problem
        solve(initialM, x1, y1, finalM);
    }
}