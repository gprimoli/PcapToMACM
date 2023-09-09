package util.analizer;

import struct.Node;
import util.Util;
import util.kaitai.*;
import struct.Graph;

import java.io.IOException;
import java.util.Arrays;

public class PcapAnalyzer {
    private final Graph macm;
    private final Pcap pcap;

    public PcapAnalyzer(String pcap) throws IOException {
        this.macm = new Graph();
        this.pcap = Pcap.fromFile(pcap);
    }

    public PcapAnalyzer(String pcap, String targetMac) throws IOException {
        this.macm = new Graph();
        this.pcap = Pcap.fromFile(pcap);
        analyze(targetMac);
    }

    public void analyze(String targetMAC) throws IOException {
        byte[] targetMACArray = Util.MACStringToByteArray(targetMAC);
        for (Pcap.Packet packet : pcap.packets()) {
            EthernetFrame ef = (EthernetFrame) packet.body();

            boolean src = Arrays.equals(targetMACArray, ef.srcMac());
            boolean dst = Arrays.equals(targetMACArray, ef.dstMac());

            if (src || dst) {
                String otherMAC = src ? Util.bytesToStringMac(ef.dstMac()) :  Util.bytesToStringMac(ef.srcMac());
//Da sostituire con uno switch per analizzare anche altri tipi di protocolli
                if (ef.etherType1() == EthernetFrame.EtherTypeEnum.IPV4) {
                    Ipv4Packet ipv4 = (Ipv4Packet) ef.body();
                    ProtocolBody protocolBody = ipv4.body();

                    String targetIp = src ? Util.byteToIP(ipv4.srcIpAddr()) : Util.byteToIP(ipv4.dstIpAddr());
                    String otherIp = src ? Util.byteToIP(ipv4.dstIpAddr()) : Util.byteToIP(ipv4.srcIpAddr());

                    Node targetNode = src ? new Node(targetMAC, targetIp, targetMAC) : new Node(otherMAC, otherIp, otherMAC);
                    Node destNode = src ? new Node(otherMAC, otherIp, otherMAC) : new Node(targetMAC, targetIp, targetMAC);

                    switch (protocolBody.protocol()) {
                        case TCP -> {
                            TcpSegment tcp = (TcpSegment) protocolBody.body();
                            macm.insertEdge(
                                    src ? tcp.srcPort() : tcp.dstPort(),
                                    true,
                                    targetNode,
                                    destNode
                            );
                        }
                        case UDP -> {
                            UdpDatagram udp = (UdpDatagram) protocolBody.body();
                            macm.insertEdge(
                                    src ? udp.srcPort() : udp.dstPort(),
                                    false,
                                    targetNode,
                                    destNode
                            );
                        }
                    }
                }
            }
        }
    }

    public Graph getMacm() {
        return macm;
    }
}
