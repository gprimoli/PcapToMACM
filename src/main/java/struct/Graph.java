package struct;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
public class Graph extends JComponent {
    private int nNode;
    private final HashMap<String, Node> nodes;
    private final LinkedList<Edge> edges;

    public Graph(Node ...nodes) {
        super();
        nNode = 0;
        this.nodes = new HashMap<>();
        this.edges = new LinkedList<>();
        for(Node n : nodes){
            insertNode(n);
        }
    }

    public void insertNode(Node n){
        if(this.nodes.putIfAbsent(n.getName(), n) == null){
            nNode++;
        }
    }
    public void removeNode(Node n){
        this.edges.removeIf(e -> e.getNodeA().equals(n) || e.getNodeB().equals(n));
        this.nodes.remove(n.getName());
        nNode--;
    }

    public void insertEdge(int port, boolean isTCP, Node na, Node nb){
        this.insertNode(na);
        this.insertNode(nb);
        na.addOpenPort(port, isTCP);
        Edge e = new Edge(port, isTCP, na, nb);
        if (!this.edges.contains(e)){
            this.edges.add(e);
        }
    }
    public boolean renameNode(Node n, String newName){
        Node el = this.nodes.get(n.getName());
        if (el != null){
            removeNode(n);
            el.rename(newName);
            insertNode(n);
            return true;
        }
        return false;
    }

    public LinkedList<Node> getNodes() {
        return new LinkedList<>(nodes.values());
    }

    public LinkedList<Edge> getEdges() {
        return edges;
    }

    public int getNumberofNodes() {
        return nNode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MACM\n");
        for (Node n : nodes.values()){
            sb.append(n).append("\n");
            sb.append("Link:\n");
            for (Edge e : edges){
                if (e.getNodeA().equals(n))
                    sb.append(e).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}




