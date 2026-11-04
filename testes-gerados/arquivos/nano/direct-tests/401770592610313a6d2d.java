/* @test    @summary Test constructor of IvParameterSpec with valid byte array    @run main IvParameterSpecWitupTest */

import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;

public class IvParameterSpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidArray();
        testConstructorWithNullArray();
        testConstructorWithEmptyArray();
        testConstructorWithOffsetAndLength();
        testConstructorWithInvalidOffset();
        testConstructorWithNegativeLength();
    }

    static void testConstructorWithValidArray() {
        byte[] iv = {1, 2, 3, 4, 5};
        IvParameterSpec spec = new IvParameterSpec(iv);
        byte[] result = spec.getIV();
        assert Arrays.equals(iv, result) : "IVs should be equal";
    }

    static void testConstructorWithNullArray() {
        try {
            new IvParameterSpec((byte[]) null);
            assert false : "Expected IllegalArgumentException for null array";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("IV missing") : "Unexpected exception message";
        }
    }

    static void testConstructorWithEmptyArray() {
        byte[] iv = {};
        IvParameterSpec spec = new IvParameterSpec(iv);
        byte[] result = spec.getIV();
        assert Arrays.equals(iv, result) : "IVs should be equal for empty array";
    }

    static void testConstructorWithOffsetAndLength() {
        byte[] iv = {10, 20, 30, 40, 50};
        IvParameterSpec spec = new IvParameterSpec(iv, 1, 3);
        byte[] expected = {20, 30, 40};
        byte[] result = spec.getIV();
        assert Arrays.equals(expected, result) : "IV should match the specified segment";
    }

    static void testConstructorWithInvalidOffset() {
        byte[] iv = {1, 2, 3};
        try {
            new IvParameterSpec(iv, 2, 2);
            assert false : "Expected IllegalArgumentException for invalid offset/length";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("IV buffer too short for given offset/length combination") : "Unexpected exception message";
        }
    }

    static void testConstructorWithNegativeLength() {
        byte[] iv = {1, 2, 3};
        try {
            new IvParameterSpec(iv, 0, -1);
            assert false : "Expected ArrayIndexOutOfBoundsException for negative length";
        } catch (ArrayIndexOutOfBoundsException e) {
            assert e.getMessage().equals("len is negative") : "Unexpected exception message";
        }
    }
}
