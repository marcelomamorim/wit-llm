/*
 * @test
 * @summary Checks ReasonFlags(DerValue) accepts a valid BIT STRING and rejects a non-BIT STRING tag with IOException
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main ReasonFlagsWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.ReasonFlags;

public class ReasonFlagsWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] validEncoding = {0x03, 0x02, 0x06, 0x40};
        ReasonFlags flags = new ReasonFlags(new DerValue(validEncoding));
        if (flags == null) {
            throw new RuntimeException("ReasonFlags(DerValue) returned null for valid BIT STRING");
        }

        byte[] octetStringEncoding = {0x04, 0x01, 0x00};
        DerValue wrongTag = new DerValue(octetStringEncoding);
        boolean thrown = false;
        try {
            new ReasonFlags(wrongTag);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for non-BIT STRING DerValue");
        }
    }
}
