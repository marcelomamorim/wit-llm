/*
 * @test
 * @summary Verify DatagramPacket.setData(byte[]) resets offset and length, returns the given buffer, and rejects null without changing state
 * @run main DatagramPacketWitupTest
 */
import java.net.DatagramPacket;

public class DatagramPacketWitupTest {
    public static void main(String[] args) {
        byte[] initial = new byte[10];
        DatagramPacket p = new DatagramPacket(initial, 2, 3);
        if (p.getOffset() != 2 || p.getLength() != 3) {
            throw new RuntimeException("unexpected precondition offset/length");
        }

        byte[] replacement = new byte[20];
        p.setData(replacement);
        if (p.getData() != replacement) {
            throw new RuntimeException("setData did not store the given buffer");
        }
        if (p.getOffset() != 0) {
            throw new RuntimeException("offset not reset to 0: " + p.getOffset());
        }
        if (p.getLength() != 20) {
            throw new RuntimeException("length not set to buf.length: " + p.getLength());
        }

        try {
            p.setData(null);
            throw new RuntimeException("NullPointerException not thrown for null buffer");
        } catch (NullPointerException e) {
            if (!"null packet buffer".equals(e.getMessage())) {
                throw new RuntimeException("unexpected NPE message: " + e.getMessage());
            }
        }

        if (p.getData() != replacement || p.getOffset() != 0 || p.getLength() != 20) {
            throw new RuntimeException("state changed after rejected null buffer");
        }

        byte[] empty = new byte[0];
        p.setData(empty);
        if (p.getData() != empty || p.getOffset() != 0 || p.getLength() != 0) {
            throw new RuntimeException("zero-length buffer not handled correctly");
        }
    }
}
