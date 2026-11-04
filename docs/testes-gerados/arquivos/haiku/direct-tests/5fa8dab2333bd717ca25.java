/*
 * @test
 * @summary Verifies DerInputStream.getBitString() for valid, mismatched-tag and empty DER input
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidBitString();
        testWrongTagThrows();
        testEmptyInputThrows();
    }

    static void testValidBitString() throws IOException {
        // tag 0x03 (BitString), length 3, unused-bits 0, payload AA BB
        byte[] der = {0x03, 0x03, 0x00, (byte) 0xAA, (byte) 0xBB};
        DerInputStream in = new DerInputStream(der);
        byte[] result = in.getBitString();
        if (result.length != 2 || result[0] != (byte) 0xAA || result[1] != (byte) 0xBB) {
            throw new RuntimeException("Unexpected bit string content");
        }
    }

    static void testWrongTagThrows() {
        // tag 0x04 (OctetString) is not a BitString tag
        byte[] der = {0x04, 0x01, 0x00};
        try {
            new DerInputStream(der).getBitString();
            throw new RuntimeException("Expected IOException for wrong tag");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testEmptyInputThrows() {
        try {
            new DerInputStream(new byte[0]).getBitString();
            throw new RuntimeException("Expected IOException for empty input");
        } catch (IOException expected) {
            // expected
        }
    }
}
