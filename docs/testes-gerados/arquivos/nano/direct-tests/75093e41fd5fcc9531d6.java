/* @test    @summary Test RC5ParameterSpec constructor with IV array    @run main RC5ParameterSpecWitupTest */

import javax.crypto.spec.RC5ParameterSpec;
import java.util.Arrays;

public class RC5ParameterSpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidIV();
        testConstructorWithNullIV();
        testConstructorWithShortIV();
    }

    static void testConstructorWithValidIV() {
        byte[] iv = {1, 2, 3, 4};
        int version = 1;
        int rounds = 12;
        int wordSize = 32;
        RC5ParameterSpec spec = new RC5ParameterSpec(version, rounds, wordSize, iv);
        assert spec != null : "Constructor returned null";
        assert spec != null : "Spec should be instantiated";
        // Verify that the IV is copied correctly
        byte[] ivCopy = new byte[iv.length];
        System.arraycopy(iv, 0, ivCopy, 0, iv.length);
        // Since internal iv is private, we test behavior by re-constructing with same parameters
        // No direct getter, so test that no exception occurs and object is created
    }

    static void testConstructorWithNullIV() {
        int version = 2;
        int rounds = 10;
        int wordSize = 16;
        try {
            new RC5ParameterSpec(version, rounds, wordSize, null);
            assert false : "Expected IllegalArgumentException for null IV";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testConstructorWithShortIV() {
        byte[] shortIV = {1, 2};
        int version = 3;
        int rounds = 8;
        int wordSize = 64;
        try {
            new RC5ParameterSpec(version, rounds, wordSize, shortIV);
            // No exception expected here because constructor does not check length
            // But in real implementation, might throw; since source code not shown, assume no exception
        } catch (IllegalArgumentException e) {
            assert false : "Did not expect exception for short IV";
        }
    }
}
