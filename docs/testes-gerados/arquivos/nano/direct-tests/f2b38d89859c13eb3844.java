/* @test    @summary Test RC2ParameterSpec constructor with valid parameters    @run main RC2ParameterSpecWitupTest */

import javax.crypto.spec.RC2ParameterSpec;
import java.util.Arrays;

public class RC2ParameterSpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParameters();
        testConstructorWithNullIV();
        testConstructorWithShortIV();
    }

    static void testConstructorWithValidParameters() {
        int effectiveKeyBits = 128;
        byte[] iv = new byte[] {1,2,3,4,5,6,7,8};
        int offset = 0;
        RC2ParameterSpec spec = new RC2ParameterSpec(effectiveKeyBits, iv, offset);
        assert spec != null : "Spec should not be null";
    }

    static void testConstructorWithNullIV() {
        int effectiveKeyBits = 128;
        byte[] iv = null;
        int offset = 0;
        try {
            new RC2ParameterSpec(effectiveKeyBits, iv, offset);
            assert false : "Expected IllegalArgumentException for null IV";
        } catch (IllegalArgumentException e) {
            assert "IV missing".equals(e.getMessage()) : "Unexpected exception message";
        }
    }

    static void testConstructorWithShortIV() {
        int effectiveKeyBits = 128;
        byte[] iv = new byte[] {1,2,3}; // too short
        int offset = 0;
        try {
            new RC2ParameterSpec(effectiveKeyBits, iv, offset);
            assert false : "Expected IllegalArgumentException for short IV";
        } catch (IllegalArgumentException e) {
            assert "IV too short".equals(e.getMessage()) : "Unexpected exception message";
        }
    }
}
