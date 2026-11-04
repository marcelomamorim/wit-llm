/* @test    @summary Test DatagramPacket constructor with byte array, length, and SocketAddress.    @run main DatagramPacketWitupTest */

import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testConstructorWithByteArrayLengthAndSocketAddress();
    }

    static void testConstructorWithByteArrayLengthAndSocketAddress() {
        byte[] buffer = new byte[] {1, 2, 3, 4, 5};
        int length = 3;
        SocketAddress address = new InetSocketAddress("localhost", 12345);
        DatagramPacket packet = new DatagramPacket(buffer, length, address);
        assert packet.buf == buffer : "Buffer should be assigned correctly";
        assert packet.offset == 0 : "Default offset should be 0";
        assert packet.length == length : "Length should match input";
        assert packet.address.equals(address) : "Address should be assigned correctly";
        assert packet.port == -1 : "Default port should be -1";
    }
}
