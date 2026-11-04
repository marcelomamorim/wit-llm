/* @test    @summary Test DatagramPacket constructor with valid parameters    @run main DatagramPacketWitupTest */

import java.net.DatagramPacket;
import java.net.InetAddress;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testConstructorValidParameters();
        testConstructorInvalidParameters();
    }

    private static void testConstructorValidParameters() {
        byte[] buffer = new byte[10];
        int offset = 2;
        int length = 5;
        try {
            DatagramPacket packet = new DatagramPacket(buffer, offset, length);
            assert packet.buf == buffer : "Buffer mismatch";
            assert packet.offset == offset : "Offset mismatch";
            assert packet.length == length : "Length mismatch";
            assert packet.address == null : "Address should be null";
            assert packet.port == -1 : "Port should be -1";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid parameters test", e);
        }
    }

    private static void testConstructorInvalidParameters() {
        byte[] buffer = new byte[10];
        int offset = 8;
        int length = 5;
        try {
            new DatagramPacket(buffer, offset, length);
            throw new AssertionError("Expected IllegalArgumentException not thrown for buffer too small");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }

        try {
            new DatagramPacket(buffer, -1, 5);
            throw new AssertionError("Expected IllegalArgumentException not thrown for negative offset");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }

        try {
            new DatagramPacket(buffer, 0, -1);
            throw new AssertionError("Expected IllegalArgumentException not thrown for negative length");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
