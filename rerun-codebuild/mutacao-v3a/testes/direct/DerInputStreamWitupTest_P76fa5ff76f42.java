/* @test    @summary Test getInteger() method of DerInputStream for valid and invalid tags    @run main DerInputStreamWitupTest_P76fa5ff76f42 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest_P76fa5ff76f42 {

    public static void main(String[] args) throws IOException {
        testValidInteger();
        testInvalidTag();
    }

    public static void testValidInteger() throws IOException {
        byte[] data = new byte[] {
            (byte) 0x02, // Tag for Integer
            0x01, // Length
            0x05 // Value
        };
        DerInputStream dis = new DerInputStream(data);
        int result = dis.getInteger();
        assert result == 5 : "Expected 5, got " + result;
    }

    public static void testInvalidTag() {
        byte[] data = new byte[] {
            (byte) 0x01, // Wrong Tag
            0x01, // Length
            0x05 // Value
        };
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getInteger();
            assert false : "Expected IOException due to wrong tag";
        } catch (IOException e) {
            assert e.getMessage().contains("DER input, Integer tag error") : "Unexpected message: " + e.getMessage();
        }
    }
}
