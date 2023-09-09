package struct;

import java.util.Objects;

public class Edge {
    private String name;
    private final Node A;
    private final Node B;
    private final Port protocol;

    public Edge(int port, boolean isTCP, Node A, Node B) {
        this.name = A.getName() + "<--->" + B.getName();
        this.protocol = new Port(port, isTCP);
        this.A = A;
        this.B = B;
    }

    public String getName() {
        return name;
    }

    public Port getProtocol() {
        return protocol;
    }

    public Node getNodeA() {
        return A;
    }

    public Node getNodeB() {
        return B;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge edge)) return false;
        return name.compareToIgnoreCase(edge.getName()) == 0
                && protocol.equals(edge.protocol)
                && (A.equals(edge.A) || A.equals(edge.B))
                && (B.equals(edge.B) || B.equals(edge.A));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getProtocol(), A, B);
    }

    @Override
    public String toString() {
        return "Edge{ Nome= " + name + " " + protocol + " " + A +" " + B + "}";
    }
}
