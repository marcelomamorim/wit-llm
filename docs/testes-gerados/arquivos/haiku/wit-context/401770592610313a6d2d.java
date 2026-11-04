/*
 * @test
 * @summary Witup-generated checks for IvParameterSpec(byte[] iv) constructor
 * @run main IvParameterSpecWitupTest
 */

import java.util.Arrays;
import javax.crypto.spec.IvParameterSpec;

public class IvParameterSpecWitupTest {

    public static void main(String[] args) throws Exception {
        testNullIvThrowsNullPointerException();
        testConstructorCopiesInput();
        testGetIVReturnsIndependentArray();
        testEmptyIvIsAccepted();
    }

    static void testNullIvThrowsNullPointerException() {
        try {
            new IvParameterSpec((byte[]) null);
            throw new RuntimeException("Expected NullPointerException for null iv");
        } catch (NullPointerException expected) {
        }
    }

    static void testConstructorCopiesInput() {
        byte[] original = {1, 2, 3, 4, 5, 6, 7, 8};
        byte[] expected = original.clone();
        IvParameterSpec spec = new IvParameterSpec(original);
        original[0] = (byte) 0x7F;
        original[7] = (byte) 0x7F;
        check(Arrays.equals(expected, spec.getIV()),
              "IV must not reflect later modifications of the input array");
    }

    static void testGetIVReturnsIndependentArray() {
        byte[] iv = {10, 20, 30, 40};
        IvParameterSpec spec = new IvParameterSpec(iv);
        byte[] first = spec.getIV();
        first[0] = 99;
        byte[] second = spec.getIV();
        check(Arrays.equals(iv, second),
              "getIV must return a copy unaffected by changes to a previously returned array");
        check(first != second, "getIV must return a new array on each call");
    }

    static void testEmptyIvIsAccepted() {
        IvParameterSpec spec = new IvParameterSpec(new byte[0]);
        check(spec.getIV().length == 0, "Empty IV must be preserved with length 0");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
