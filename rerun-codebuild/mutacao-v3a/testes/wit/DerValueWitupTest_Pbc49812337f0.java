/* @test    @summary Test getT61String() method in DerValue class for expected behavior and exception handling    @run main DerValueWitupTest_Pbc49812337f0 */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_Pbc49812337f0 {

    public static void main(String[] args) throws Exception {
        testValidT61String();
        testInvalidTagThrowsIOException();
    }

    private static void testValidT61String() throws Exception {
        byte[] t61Data = { (byte)0x14, 0x05, 0x48, 0x65, 0x6C, 0x6C, 0x6F }; // Tag=0x14, length=5, data="Hello"
        DerValue dv = new DerValue(t61Data);
        dv.tag = DerValue.tag_T61String; // ensure tag is T61String
        String result = dv.getT61String();
        if (!"Hello".equals(result)) {
            throw new AssertionError("Expected 'Hello', got: " + result);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        byte[] invalidData = { (byte)0x02, 0x01, 0x01 }; // Tag=0x02 (Integer), length=1, data=0x01
        try {
            DerValue dv = new DerValue(invalidData);
            dv.tag = 0x02; // forcibly set tag to Integer
            dv.getT61String();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("not T61")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
