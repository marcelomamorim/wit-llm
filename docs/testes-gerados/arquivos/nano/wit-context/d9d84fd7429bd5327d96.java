/* @test    @summary Test for DerInputStream.getUTCTime() method to verify correct behavior and exception handling    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;
import java.util.Date;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidUTCTime();
        testInvalidTag();
    }

    private static void testValidUTCTime() throws Exception {
        byte[] validData = new byte[] { (byte)0x17, 0x0D, 0x32, 0x30, 0x30, 0x39, 0x30, 0x35, 0x30, 0x35, 0x30, 0x30, 0x30, 0x5A };
        DerInputStream dis = new DerInputStream(validData);
        Date date = dis.getUTCTime();
        if (date == null) {
            throw new AssertionError("Expected non-null Date for valid UTCTime");
        }
        System.out.println("Valid UTCTime test passed.");
    }

    private static void testInvalidTag() {
        byte[] invalidTagData = new byte[] { (byte)0x30, 0x0D, 0x17, 0x0D, 0x32, 0x30, 0x30, 0x39, 0x30, 0x35, 0x30, 0x35, 0x30, 0x30, 0x30, 0x5A };
        try {
            DerInputStream dis = new DerInputStream(invalidTagData);
            dis.getUTCTime();
            throw new AssertionError("Expected IOException due to invalid tag");
        } catch (IOException e) {
            System.out.println("Invalid tag test passed with exception: " + e.getMessage());
        }
    }
}
