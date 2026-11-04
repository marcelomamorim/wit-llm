/* @test    @summary Test getBitString() method of DerInputStream for valid and invalid DER data    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidBitString();
        testInvalidTag();
    }

    private static void testValidBitString() throws IOException {
        byte[] validData = new byte[] { (byte)0x03, 0x02, 0x05, 0x06 };
        DerInputStream dis = new DerInputStream(validData);
        byte[] result = dis.getBitString();
        if (result.length != 2 || result[0] != 0x05 || result[1] != 0x06) {
            throw new AssertionError("Valid bit string test failed");
        }
        System.out.println("Valid bit string test passed");
    }

    private static void testInvalidTag() {
        byte[] invalidData = new byte[] { (byte)0x04, 0x01, 0x00 };
        try {
            DerInputStream dis = new DerInputStream(invalidData);
            dis.getBitString();
            throw new AssertionError("Expected IOException for invalid tag not thrown");
        } catch (IOException e) {
            if (!e.getMessage().equals("DER input not an bit string")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            System.out.println("Invalid tag test passed");
        }
    }
}
