/* @test    @summary Test constructor DatagramPacket(byte[] buf, int offset, int length) for correct initialization and behavior.    @run main DatagramPacketWitupTest */

import java.net.DatagramPacket;
import java.util.Arrays;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParameters();
        testConstructorWithZeroLengthBuffer();
        testConstructorWithNegativeOffset();
        testConstructorWithOffsetExceedingBuffer();
        testConstructorWithNullBuffer();
    }

    static void testConstructorWithValidParameters() {
        byte[] buffer = new byte[] {1, 2, 3, 4, 5};
        int offset = 1;
        int length = 3;
        DatagramPacket packet = new DatagramPacket(buffer, offset, length);
        assert packet.buf == buffer : "Buffer reference mismatch";
        assert packet.offset == offset : "Offset mismatch";
        assert packet.length == length : "Length mismatch";
        assert packet.address == null : "Address should be null";
        assert packet.port == -1 : "Port should be -1";
        assert Arrays.equals(Arrays.copyOfRange(packet.buf, packet.offset, packet.offset + packet.length), new byte[] {2, 3, 4}) : "Data slice mismatch";
    }

    static void testConstructorWithZeroLengthBuffer() {
        byte[] buffer = new byte[0];
        int offset = 0;
        int length = 0;
        DatagramPacket packet = new DatagramPacket(buffer, offset, length);
        assert packet.buf == buffer : "Buffer reference mismatch";
        assert packet.offset == offset : "Offset mismatch";
        assert packet.length == length : "Length mismatch";
        assert packet.address == null : "Address should be null";
        assert packet.port == -1 : "Port should be -1";
    }

    static void testConstructorWithNegativeOffset() {
        byte[] buffer = new byte[] {1, 2, 3};
        int offset = -1;
        int length = 2;
        try {
            new DatagramPacket(buffer, offset, length);
            assert false : "Expected ArrayIndexOutOfBoundsException for negative offset";
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    static void testConstructorWithOffsetExceedingBuffer() {
        byte[] buffer = new byte[] {1, 2, 3};
        int offset = 2;
        int length = 2;
        try {
            new DatagramPacket(buffer, offset, length);
            assert false : "Expected ArrayIndexOutOfBoundsException for offset + length exceeding buffer length";
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    static void testConstructorWithNullBuffer() {
        byte[] buffer = null;
        int offset = 0;
        int length = 1;
        try {
            new DatagramPacket(buffer, offset, length);
            assert false : "Expected NullPointerException for null buffer";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
