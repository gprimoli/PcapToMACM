package util;
import com.google.common.net.InetAddresses;

public class Util {
    public static boolean isPrivateV4Address(String ip) {
        int address = InetAddresses.coerceToInteger(InetAddresses.forString(ip));
        return (((address >>> 24) & 0xFF) == 10)
                || ((((address >>> 24) & 0xFF) == 172)
                && ((address >>> 16) & 0xFF) >= 16
                && ((address >>> 16) & 0xFF) <= 31)
                || ((((address >>> 24) & 0xFF) == 192)
                && (((address >>> 16) & 0xFF) == 168));
    }

    public static String bytesToStringMac(byte[] mac) {
        StringBuilder sb = new StringBuilder(18);
        for (byte b : mac) {
            if (!sb.isEmpty()) {
                sb.append(':');
            }
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String byteToIP(byte[] ip) {
        return (ip[0] & 0xFF) + "." +
                (ip[1] & 0xFF) + "." +
                (ip[2] & 0xFF) + "." +
                (ip[3] & 0xFF);
    }

    public static byte[] MACStringToByteArray(String address) {
        String[] addrs = address.split(":");
        byte[] mac_bytes = new byte[6];
        for (int i = 0; i < addrs.length; i++) {
            mac_bytes[i] = Integer.decode("0x" + addrs[i]).byteValue();
        }//from  w w  w . j a va2s  .c  o  m
        return mac_bytes;
    }
}
