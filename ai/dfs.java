import java.util.*;

class dfs {
    private LinkedList<Integer> l[];
    private boolean v[];
    private ArrayList<Integer> path; 
    void newgraph(int n) {
        l = new LinkedList[n];
        v = new boolean[n];
        path = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            l[i] = new LinkedList<Integer>();
            v[i] = false;
        }
    }
    void edge(int i, int j) {
        l[i].add(j);
        l[j].add(i);
    }
    void DFS(int x, int end) {
        v[x] = true;
        path.add(x);

        if (x == end) {
            printPath();
            return;
        }

        Iterator<Integer> it = l[x].listIterator();
        while (it.hasNext()) {
            int y = it.next();
            if (!v[y]) {
                DFS(y, end);
            }
        }
        path.remove(path.size() - 1);
    }

    void printPath() {
        System.out.print("Path: ");
        for (int node : path) {
            System.out.print(node + " ");
        }
        System.out.println();
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        dfs ob = new dfs();
        System.out.println("Enter the number of vertices:");
        int n = sc.nextInt();
        ob.newgraph(n);
        System.out.println("Enter the number of edges:");
        int e = sc.nextInt();
        System.out.println("Enter the edges");
        for (int i = 0; i < e; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            ob.edge(x, y);
        }
        System.out.println("Enter the starting vertex:");
        int start = sc.nextInt();
        System.out.println("Enter the ending vertex:");
        int end = sc.nextInt();
        sc.close();

        ob.DFS(start, end);
    }
}
