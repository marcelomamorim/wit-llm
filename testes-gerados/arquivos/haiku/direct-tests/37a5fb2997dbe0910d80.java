/*
 * @test
 * @summary Verifies DerValue.toDerInputStream accepts SEQUENCE and SET and rejects other tags
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        DerValue seq = new DerValue(new byte[] {0x30, 0x00});
        if (seq.toDerInputStream() == null) {
            throw new RuntimeException("SEQUENCE returned null DerInputStream");
        }

        DerValue set = new DerValue(new byte[] {0x31, 0x00});
        if (set.toDerInputStream() == null) {
            throw new RuntimeException("SET returned null DerInputStream");
        }

        expectRejected(new byte[] {0x02, 0x01, 0x05}, "INTEGER");
        expectRejected(new byte[] {0x04, 0x00}, "OCTET STRING");
        expectRejected(new byte[] {0x05, 0x00}, "NULL");
    }

    private static void expectRejected(byte[] encoding, String name) throws Exception {
        DerValue value = new DerValue(encoding);
        try {
            value.toDerInputStream();
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.startsWith("toDerInputStream rejects tag type")) {
                throw new RuntimeException("Unexpected message for " + name + ": " + msg);
            }
            return;
        }
        throw new RuntimeException("No IOException for " + name);
    }
}
