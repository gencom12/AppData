import java.util.*;

public class bfs {
    private int v;
    private LinkedList<Integer> l[];
    private Queue<Integer> q;
    private int[] parent;

    void newgraph(int n) {
        v = n;
        l = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            l[i] = new LinkedList<>();
        }
        q = new LinkedList<>();
        parent = new int[v];
    }

    void edge(int a, int b) {
        l[a].add(b);
        l[b].add(a);
    }

    void BFS(int x, int y) {
        boolean val[] = new boolean[v];
        Arrays.fill(val, false);
        Arrays.fill(parent, -1);

        val[x] = true;
        q.add(x);

        while (!q.isEmpty()) {
            int n = q.poll();

            for (int i = 0; i < l[n].size(); i++) {
                int a = l[n].get(i);
                if (!val[a]) {
                    val[a] = true;
                    q.add(a);
                    parent[a] = n;
                }
            }
        }

        path(x, y);
    }

    void path(int x, int y) {
        System.out.println("Path from " + x + " to " + y + ":");
        pathcheck(x, y);
        System.out.println();
    }

    void pathcheck(int x, int y) {
        if (y == -1) {
            System.out.println("No path exists.");
            return;
        }

        if (x == y) {
            System.out.print(y + " ");
            return;
        }

        pathcheck(x, parent[y]);
        System.out.print(y + " ");
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        bfs ob = new bfs();
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
        int x = sc.nextInt();
        System.out.println("Enter the ending vertex:");
        int y = sc.nextInt();
        sc.close();

        ob.BFS(x, y);
    }
}
