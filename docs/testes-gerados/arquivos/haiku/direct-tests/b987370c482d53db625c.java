/*
 * @test
 * @summary Verifies the public (buf, offset, length) constructor of DatagramPacket: stored state, accepted boundaries and IllegalArgumentException for invalid offset/length.
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] buf = new byte[10];

        DatagramPacket p = new DatagramPacket(buf, 2, 5);
        check(p.getData() == buf, "getData() must return the same buffer");
        check(p.getOffset() == 2, "offset must be 2 but was " + p.getOffset());
        check(p.getLength() == 5, "length must be 5 but was " + p.getLength());
        check(p.getAddress() == null, "address must be null for receive packet");
        check(p.getPort() == -1, "port must be -1 for receive packet");

        DatagramPacket atEnd = new DatagramPacket(buf, 5, 5);
        check(atEnd.getOffset() == 5 && atEnd.getLength() == 5, "offset+length == buf.length must be accepted");

        DatagramPacket zero = new DatagramPacket(buf, 10, 0);
        check(zero.getOffset() == 10 && zero.getLength() == 0, "zero length at buffer end must be accepted");

        DatagramPacket twoArg = new DatagramPacket(buf, 4);
        check(twoArg.getOffset() == 0 && twoArg.getLength() == 4, "two-arg constructor must use offset 0");

        expectIAE(buf, -1, 2);
        expectIAE(buf, 0, -1);
        expectIAE(buf, 6, 5);
        expectIAE(buf, 11, 0);
        expectIAE(buf, Integer.MAX_VALUE, 1);
        expectIAE(buf, 0, 11);
    }

    static void expectIAE(byte[] b, int off, int len) {
        boolean thrown = false;
        try {
            new DatagramPacket(b, off, len);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalArgumentException expected for offset=" + off + ", length=" + len);
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
