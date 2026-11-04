/*
 * @test
 * @summary Validates DistributionPointName(DerValue) decoding of fullName and relativeName choices and rejection of invalid tags
 * @modules java.base/sun.security.util java.base/sun.security.x509
 * @run main DistributionPointNameWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.DistributionPointName;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] fullName = {
            (byte)0xA0, 0x09,
            0x30, 0x07,
            (byte)0x82, 0x05, 'a', '.', 'c', 'o', 'm'
        };
        new DistributionPointName(new DerValue(fullName));

        byte[] relativeName = {
            (byte)0xA1, 0x0C,
            0x31, 0x0A,
            0x30, 0x08,
            0x06, 0x03, 0x55, 0x04, 0x03,
            0x0C, 0x01, 'x'
        };
        new DistributionPointName(new DerValue(relativeName));

        expectIOException(new byte[]{(byte)0x80, 0x05, 'a', '.', 'c', 'o', 'm'});
        expectIOException(new byte[]{(byte)0x82, 0x05, 'a', '.', 'c', 'o', 'm'});
        expectIOException(new byte[]{(byte)0xA2, 0x00});
        expectIOException(new byte[]{0x30, 0x00});
    }

    static void expectIOException(byte[] enc) throws Exception {
        boolean thrown = false;
        try {
            new DistributionPointName(new DerValue(enc));
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IOException");
        }
    }
}
