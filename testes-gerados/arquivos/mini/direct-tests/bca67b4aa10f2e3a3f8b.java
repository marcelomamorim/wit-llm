/*
 * @test
 * @summary Test GCMParameterSpec constructor with int and byte[]
 * @run main GCMParameterSpecWitupTest
 */

import javax.crypto.spec.GCMParameterSpec;

public class GCMParameterSpecWitupTest {
    public static void main(String[] args) throws Exception {
        testValidConstruction();
        testNullSrcThrows();
    }

    private static void testValidConstruction() {
        byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int tLen = 128;
        GCMParameterSpec spec = new GCMParameterSpec(tLen, iv);
        if (spec == null) {
            throw new RuntimeException("GCMParameterSpec constructor returned null");
        }
    }

    private static void testNullSrcThrows() {
        try {
            new GCMParameterSpec(128, null);
            throw new RuntimeException("Expected IllegalArgumentException for null src");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("src array is null")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }
}
