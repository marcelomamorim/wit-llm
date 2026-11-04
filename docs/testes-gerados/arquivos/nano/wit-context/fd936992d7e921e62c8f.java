/* @test    @summary Test DatagramPacket(byte[] buf, int length) constructor for normal and exceptional behavior    @run main DatagramPacketWitupTest */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testConstructorWithInvalidLength();
    }

    private static void testValidConstructor() {
        byte[] buffer = {1, 2, 3, 4, 5};
        int length = 3;
        DatagramPacket packet = new DatagramPacket(buffer, length);
        assert packet.buf == buffer : "Buffer should be assigned correctly";
        assert packet.offset == 0 : "Default offset should be 0";
        assert packet.length == length : "Length should match input";
        assert packet.address == null : "Address should be null";
        assert packet.port == -1 : "Port should be -1";
    }

    private static void testConstructorWithInvalidLength() {
        byte[] buffer = {1, 2, 3};
        int invalidLength = 5; // greater than buffer length
        try {
            new DatagramPacket(buffer, invalidLength);
            assert false : "Expected IllegalArgumentException for length > buffer.length";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        int negativeLength = -1;
        try {
            new DatagramPacket(buffer, negativeLength);
            assert false : "Expected IllegalArgumentException for negative length";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
