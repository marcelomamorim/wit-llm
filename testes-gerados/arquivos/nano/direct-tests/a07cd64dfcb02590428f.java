/* @test    @summary Test constructor RC2ParameterSpec(int, byte[]) with valid inputs    @run main RC2ParameterSpecWitupTest */

import javax.crypto.spec.RC2ParameterSpec;
import java.util.Arrays;

public class RC2ParameterSpecWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNullIV();
        testShortIV();
    }

    static void testValidConstructor() {
        int keySize = 128;
        byte[] iv = new byte[] {1,2,3,4,5,6,7,8};
        RC2ParameterSpec spec = new RC2ParameterSpec(keySize, iv);
        assert spec != null : "Constructor returned null";
    }

    static void testNullIV() {
        int keySize = 128;
        byte[] iv = null;
        try {
            new RC2ParameterSpec(keySize, iv);
            assert false : "Expected IllegalArgumentException for null IV";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("IV missing") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testShortIV() {
        int keySize = 128;
        byte[] iv = new byte[] {1,2,3};
        try {
            new RC2ParameterSpec(keySize, iv);
            assert false : "Expected IllegalArgumentException for short IV";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("IV too short") : "Unexpected message: " + e.getMessage();
        }
    }
}
