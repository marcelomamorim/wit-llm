/* @test    @summary Test GCMParameterSpec constructor with valid inputs    @run main GCMParameterSpecWitupTest */

import javax.crypto.spec.GCMParameterSpec;
import java.util.Arrays;

public class GCMParameterSpecWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNullSrcThrowsException();
    }

    static void testValidConstructor() {
        byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};
        int tLen = 128;
        GCMParameterSpec spec = new GCMParameterSpec(tLen, iv);
        assert spec != null : "Constructor returned null";
        assert spec instanceof GCMParameterSpec : "Object is not instance of GCMParameterSpec";
    }

    static void testNullSrcThrowsException() {
        int tLen = 128;
        try {
            new GCMParameterSpec(tLen, null);
            assert false : "Expected IllegalArgumentException for null src";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("src array is null") : "Unexpected exception message";
        }
    }
}
