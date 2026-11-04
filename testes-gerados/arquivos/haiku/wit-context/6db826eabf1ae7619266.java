/*
 * @test
 * @summary Witup-derived checks for DistributionPointName(DerValue) constructor: invalid tags and valid fullName encoding
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main DistributionPointNameWitupTest
 */

import java.io.IOException;

import sun.security.util.DerValue;
import sun.security.x509.DistributionPointName;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) throws Exception {
        expectIOException(new byte[] {0x30, 0x00});
        expectIOException(new byte[] {(byte) 0x80, 0x00});
        expectIOException(new byte[] {(byte) 0xA2, 0x00});
        expectIOException(new byte[] {(byte) 0xA1, 0x00, 0x01});

        DerValue valid = new DerValue(new byte[] {(byte) 0xA0, 0x03, (byte) 0x82, 0x01, 0x61});
        new DistributionPointName(valid);
    }

    private static void expectIOException(byte[] encoding) throws Exception {
        DerValue value = new DerValue(encoding);
        try {
            new DistributionPointName(value);
        } catch (IOException expected) {
            return;
        }
        throw new AssertionError("Expected IOException for encoding of length " + encoding.length);
    }
}
