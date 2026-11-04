/* @test    @summary Test DatagramPacket constructor with byte array and length    @run main DatagramPacketWitupTest */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testConstructorWithByteArrayAndLength();
    }

    public static void testConstructorWithByteArrayAndLength() {
        byte[] buffer = {1, 2, 3, 4, 5};
        int length = 3;
        DatagramPacket packet = new DatagramPacket(buffer, length);
        assert packet.buf == buffer : "Buffer reference mismatch";
        assert packet.offset == 0 : "Default offset should be 0";
        assert packet.length == length : "Length mismatch";
        assert packet.address == null : "Default address should be null";
        assert packet.port == -1 : "Default port should be -1";
    }
}
