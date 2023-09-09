package struct;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class Node extends JComponent {
    private String name;
    private final String ip;
    private final String mac;
    private final ArrayList<Port> openPort;

    public Node(String name, String ip, String MAC) {
        super();
        this.name = name;
        this.ip = ip;
        this.mac = MAC;
        this.openPort = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getMac() {
        return mac;
    }

    public void rename(String name) {
        this.name = name;
    }

    public void addOpenPort(int nPort, boolean isTCP) {
        Port tmp = new Port(nPort, isTCP);
        if (!openPort.contains(tmp))
            openPort.add(new Port(nPort, isTCP));
    }

    public ArrayList<Port> getOpenPort() {
        return openPort;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node node)) return false;
        return this.mac.compareToIgnoreCase(node.mac) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getMac());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Nodo {Nome: " + name + " | MAC: " + mac + " | IP: " + ip);
        sb.append(" Open Ports = ");
        for (Port p : openPort)
            sb.append(p);
        sb.append(" }");
        return sb.toString();
    }
}
