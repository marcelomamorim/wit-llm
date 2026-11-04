/*
 * @test
 * @summary Verifies the observable state produced by the DatagramPacket(byte[], int, SocketAddress) constructor
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class DatagramPacketWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] buf = new byte[10];
        InetSocketAddress dest = new InetSocketAddress(InetAddress.getLoopbackAddress(), 4567);

        DatagramPacket p = new DatagramPacket(buf, 5, dest);
        check(p.getData() == buf, "data buffer must be the same array");
        check(p.getOffset() == 0, "offset must be 0");
        check(p.getLength() == 5, "length must be 5");
        check(p.getPort() == 4567, "port must be 4567");
        check(InetAddress.getLoopbackAddress().equals(p.getAddress()), "address must be loopback");

        SocketAddress sa = p.getSocketAddress();
        check(dest.equals(sa), "socket address must equal the one passed in");

        DatagramPacket full = new DatagramPacket(buf, buf.length, dest);
        check(full.getLength() == buf.length, "length must equal buffer length");
        check(full.getOffset() == 0, "offset must be 0 for full buffer");

        DatagramPacket empty = new DatagramPacket(buf, 0, dest);
        check(empty.getLength() == 0, "zero length must be preserved");
        check(empty.getPort() == 4567, "port must be preserved for zero length");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
