/*
 * @test
 * @summary Verifies DatagramPacket.setData(byte[]) rejects null and resets offset and length for a valid buffer
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] original = new byte[10];
        DatagramPacket packet = new DatagramPacket(original, 2, 4);

        byte[] replacement = new byte[16];
        packet.setData(replacement);

        if (packet.getData() != replacement) {
            throw new AssertionError("getData() should return the new buffer");
        }
        if (packet.getOffset() != 0) {
            throw new AssertionError("offset should be reset to 0, was " + packet.getOffset());
        }
        if (packet.getLength() != replacement.length) {
            throw new AssertionError("length should equal buf.length, was " + packet.getLength());
        }

        boolean thrown = false;
        try {
            packet.setData(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("setData(null) should throw NullPointerException");
        }

        if (packet.getData() != replacement) {
            throw new AssertionError("buffer should be unchanged after rejected null");
        }
        if (packet.getLength() != replacement.length) {
            throw new AssertionError("length should be unchanged after rejected null");
        }
    }
}
