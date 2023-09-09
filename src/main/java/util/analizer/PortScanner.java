package util.analizer;

import struct.Node;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PortScanner {
    public static void runPortScan(Node n, int nbrPortMaxToScan) {
        ConcurrentLinkedQueue<Object> openPorts = new ConcurrentLinkedQueue<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        AtomicInteger port = new AtomicInteger(0);
        while (port.get() < nbrPortMaxToScan) {
            final int currentPort = port.getAndIncrement();
            executorService.submit(() -> {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(n.getIp(), currentPort), 10000);
                    socket.close();
                    openPorts.add(currentPort);
                } catch (IOException e) {
                    System.err.println(e);
                }
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Integer> openPortList = new ArrayList<>();
        while (!openPorts.isEmpty()) {
            openPortList.add((Integer) openPorts.poll());
        }
        openPortList.forEach(p -> n.addOpenPort(p, true));
    }
}
