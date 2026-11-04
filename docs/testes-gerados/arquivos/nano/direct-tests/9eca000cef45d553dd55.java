/* @test    @summary Test DerInputStream.getT61String() with valid T61-encoded data    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testValidT61String();
        testEmptyData();
        testInvalidData();
    }

    static void testValidT61String() throws IOException {
        byte[] data = {0x14, 0x05, 0x48, 0x65, 0x6C, 0x6C, 0x6F}; // T61 string "Hello"
        DerInputStream dis = new DerInputStream(data);
        String result = dis.getT61String();
        if (!"Hello".equals(result)) {
            throw new AssertionError("Expected 'Hello', got: " + result);
        }
    }

    static void testEmptyData() {
        byte[] data = {};
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getT61String();
            throw new AssertionError("Expected IOException for empty data");
        } catch (IOException e) {
            // expected
        }
    }

    static void testInvalidData() {
        byte[] data = {0x14, 0x02, 0xFF}; // incomplete T61 string
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getT61String();
            throw new AssertionError("Expected IOException for invalid data");
        } catch (IOException e) {
            // expected
        }
    }
}
