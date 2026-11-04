/*
 * @test
 * @summary Round-trip encoding of a relative-name DistributionPointName must reproduce the original DER bytes
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main DistributionPointNameWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.DistributionPointName;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] input = {
            (byte) 0xA1, 0x0C,
            0x31, 0x0A,
            0x30, 0x08,
            0x06, 0x03, 0x55, 0x04, 0x03,
            0x0C, 0x01, 0x78
        };

        DistributionPointName dpn = new DistributionPointName(new DerValue(input));

        DerOutputStream first = new DerOutputStream();
        dpn.encode(first);
        byte[] encoded = first.toByteArray();

        if (!java.util.Arrays.equals(input, encoded)) {
            throw new RuntimeException("Round-trip encoding differs from input");
        }

        DerOutputStream second = new DerOutputStream();
        dpn.encode(second);
        if (!java.util.Arrays.equals(encoded, second.toByteArray())) {
            throw new RuntimeException("Repeated encoding is not deterministic");
        }
    }
}
