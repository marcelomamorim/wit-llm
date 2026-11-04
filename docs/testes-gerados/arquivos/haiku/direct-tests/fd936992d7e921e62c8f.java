/*
 * @test
 * @summary DatagramPacket(byte[], int) delegates to the offset constructor with offset 0 and exposes the buffer, length and offset
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] buf = new byte[16];

        DatagramPacket p = new DatagramPacket(buf, 8);
        check(p.getData() == buf, "buffer must be the same array");
        check(p.getLength() == 8, "length must be 8");
        check(p.getOffset() == 0, "offset must be 0");

        DatagramPacket ref = new DatagramPacket(buf, 0, 8);
        check(p.getOffset() == ref.getOffset(), "offset must match 3-arg constructor");
        check(p.getLength() == ref.getLength(), "length must match 3-arg constructor");
        check(p.getData() == ref.getData(), "data must match 3-arg constructor");

        DatagramPacket full = new DatagramPacket(buf, buf.length);
        check(full.getLength() == buf.length, "full-length packet must have buf.length");
        check(full.getOffset() == 0, "full-length packet offset must be 0");

        DatagramPacket zero = new DatagramPacket(buf, 0);
        check(zero.getLength() == 0, "zero length must be preserved");
        check(zero.getOffset() == 0, "zero length offset must be 0");

        try {
            new DatagramPacket(buf, -1);
            throw new RuntimeException("negative length must be rejected");
        } catch (IllegalArgumentException expected) {
        }

        try {
            new DatagramPacket(new byte[4], 8);
            throw new RuntimeException("length greater than buf.length must be rejected");
        } catch (IllegalArgumentException expected) {
        }
    }
}
