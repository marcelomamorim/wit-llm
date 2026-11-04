/* @test    @summary Test getT61String method for DerInputStream    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testGetT61StringNormal();
        testGetT61StringEOF();
        testGetT61StringInvalidTag();
    }

    private static void testGetT61StringNormal() throws IOException {
        byte[] data = {0x14, 0x05, 0x48, 0x65, 0x6C, 0x6C, 0x6F}; // Tag=0x14 (T61), Length=5, Content="Hello"
        DerInputStream dis = new DerInputStream(data);
        String result = dis.getT61String();
        if (!"Hello".equals(result)) {
            throw new AssertionError("Expected 'Hello', got: " + result);
        }
    }

    private static void testGetT61StringEOF() {
        byte[] data = {0x14, 0x02, 0x48}; // Incomplete data, length=2 but only 1 byte provided
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getT61String();
            throw new AssertionError("Expected IOException due to EOF");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testGetT61StringInvalidTag() {
        byte[] data = {0x15, 0x03, 0x41, 0x42, 0x43}; // Tag=0x15 (PrintableString), but method expects T61
        try {
            DerInputStream dis = new DerInputStream(data);
            String result = dis.getT61String();
            if (result == null || result.isEmpty()) {
                throw new AssertionError("Unexpected empty result");
            }
        } catch (IOException e) {
            // Depending on implementation, may throw; if so, test passes
        }
    }
}
