/* @test    @summary Test getUTCTime() method of DerInputStream for valid and invalid tags    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;
import java.util.Date;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidUTCTime();
        testInvalidTag();
    }

    private static void testValidUTCTime() throws IOException {
        byte[] validData = new byte[] { (byte)0x17, 0x0D, 0x32, 0x30, 0x30, 0x39, 0x30, 0x35, 0x30, 0x39, 0x30, 0x30, 0x5A };
        DerInputStream dis = new DerInputStream(validData);
        Date date = dis.getUTCTime();
        assert date != null : "Expected non-null Date for valid UTC time";
    }

    private static void testInvalidTag() {
        byte[] invalidData = new byte[] { (byte)0x18, 0x0D, 0x32, 0x30, 0x30, 0x39, 0x30, 0x35, 0x30, 0x39, 0x30, 0x30, 0x5A };
        try {
            DerInputStream dis = new DerInputStream(invalidData);
            dis.getUTCTime();
            assert false : "Expected IOException due to invalid tag";
        } catch (IOException e) {
            assert e.getMessage().contains("DER input, UTCtime tag invalid") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
