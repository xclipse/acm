import java.util.Arrays;
import java.util.Scanner;


public class POJ1935 {

  // 1935 out of time
  public static void main(String[] args) {

    Scanner s = new Scanner(System.in);
    int nodeNum = s.nextInt();
    nodes = new Node[nodeNum + 1];
    for (int i = 1; i < nodes.length; i++) {
      nodes[i] = new Node(i);
    }
    int rootId = s.nextInt();
    for (int i = 1; i < nodeNum; i++) {
      int a = s.nextInt();
      int b = s.nextInt();
      int w = s.nextInt();
      nodes[a].add(nodes[b], w);
      nodes[b].add(nodes[a], w);
    }
    int tarNum = s.nextInt();
    for (int i = 0; i < tarNum; i++) {
      nodes[s.nextInt()].tar = true;
    }
    
    System.out.println(traverse(nodes[rootId], null)[1]);
    s.close();
  }

  public static Node[] nodes;
  static class Node{
    public Node(int id) {
      this.id = id;
    }
    int id;
    boolean tar;
    
    private Node[] nodes = new Node[1];
    private int[] weight = new int[1];
    private  int nodeSize = 0;
    public void add(Node n, int w){
      if(nodes.length == nodeSize){
        nodes = Arrays.copyOf(nodes, nodes.length * 2);
        weight = Arrays.copyOf(weight, nodes.length * 2);
      }
      nodes[nodeSize] = n;
      weight[nodeSize] = w;
      nodeSize++;
    }
    public int getSize(){
      return nodeSize;
    }
    
  }


  public static int[] traverse(Node root, Node pre){
    int maxPath = 0;
    int allPath = 0;
    int path = 0;
    int noRetPath = 0;
    int[] result = null;

    for (int i = 0; i < root.getSize(); i++) {
      Node tar =root.nodes[i];
      int weight = root.weight[i];
      if(tar == pre){
        continue;
      }
      result = traverse(tar, root);
      if(!tar.tar && result[0] == 0){
        continue;
      }
      path = result[0] + 2 * weight;
      allPath += path;
      noRetPath = result[0] + weight - result[1];
      if(maxPath < noRetPath){
        maxPath = noRetPath;
      }
    }
    
    return new int[]{allPath, allPath - maxPath};
  }
}

