/*
 * @test
 * @summary Verifies DatagramPacket(byte[], int, SocketAddress) stores valid arguments and rejects invalid lengths
 * @run main DatagramPacketWitupTest
 */
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class DatagramPacketWitupTest {

    public static void main(String[] args) throws Exception {
        InetSocketAddress dest = new InetSocketAddress(InetAddress.getLoopbackAddress(), 9999);

        byte[] buf = new byte[10];
        DatagramPacket p = new DatagramPacket(buf, 5, dest);
        check(p.getData() == buf, "data buffer should be the same array");
        check(p.getOffset() == 0, "offset should be 0");
        check(p.getLength() == 5, "length should be 5");
        check(p.getPort() == 9999, "port should be 9999");
        check(p.getAddress().equals(InetAddress.getLoopbackAddress()), "address should be loopback");

        expectIllegalArgument(new byte[4], 5, dest);
        expectIllegalArgument(buf, -1, dest);
    }

    static void expectIllegalArgument(byte[] buf, int length, InetSocketAddress dest) {
        try {
            new DatagramPacket(buf, length, dest);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for length " + length);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
