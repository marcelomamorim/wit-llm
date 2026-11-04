/*
 * @test
 * @summary Verify DatagramPacket.setPort accepts the range 0..0xFFFF and rejects values outside it
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] buf = new byte[16];
        DatagramPacket p = new DatagramPacket(buf, 16);

        p.setPort(0);
        if (p.getPort() != 0) {
            throw new RuntimeException("port 0 not stored, got " + p.getPort());
        }

        p.setPort(0xFFFF);
        if (p.getPort() != 0xFFFF) {
            throw new RuntimeException("port 0xFFFF not stored, got " + p.getPort());
        }

        p.setPort(8080);
        if (p.getPort() != 8080) {
            throw new RuntimeException("port 8080 not stored, got " + p.getPort());
        }

        expectIllegalArgument(p, -1);
        expectIllegalArgument(p, 0x10000);
        expectIllegalArgument(p, Integer.MIN_VALUE);

        if (p.getPort() != 8080) {
            throw new RuntimeException("port changed after rejected value, got " + p.getPort());
        }
    }

    static void expectIllegalArgument(DatagramPacket p, int port) {
        try {
            p.setPort(port);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for port " + port);
    }
}
