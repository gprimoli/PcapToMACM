package struct;

import java.util.Objects;

public class Port {
    private final int port;
    private final boolean isTCP;

    public Port(int port, boolean isTCP) {
        this.port = port;
        this.isTCP = isTCP;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ ");
        if (isTCP) {
            sb.append("TCP n. ").append(port).append(" Service ");
            switch (port) {
                case 21 -> sb.append("SSH");
                case 22 -> sb.append("FTP");
                case 80 -> sb.append("HTTP");
                case 443 -> sb.append("HTTPS");
                case 1883 -> sb.append("MQTT_FROM");
                case 1884 -> sb.append("MQTT_TO");
                default -> sb.append("UNKNOWN_TCP");
            }
        } else {
            sb.append("UDP n. ").append(port).append(" Service ");
            switch (port) {
                case 67 -> sb.append("DHCP_SERVER");
                case 68 -> sb.append("DHCP_CLIENT");
                default -> sb.append("UNKNOWN_UDP");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Port port1)) return false;
        return port == port1.port && isTCP == port1.isTCP;
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, isTCP);
    }
}
