/* @test    @summary Test Inflater.setInput(byte[], int, int) method for correct behavior and exception handling    @run main InflaterWitupTest */

import java.util.zip.Inflater;

public class InflaterWitupTest {

    public static void main(String[] args) {
        testSetInputValid();
        testSetInputNegativeOffset();
        testSetInputNegativeLength();
        testSetInputOffsetBeyondArray();
        testSetInputOffsetPlusLengthBeyondArray();
    }

    static void testSetInputValid() {
        Inflater inflater = new Inflater();
        byte[] data = {1, 2, 3, 4, 5};
        try {
            inflater.setInput(data, 1, 3); // should succeed
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid input", e);
        }
    }

    static void testSetInputNegativeOffset() {
        Inflater inflater = new Inflater();
        byte[] data = {1, 2, 3};
        boolean thrown = false;
        try {
            inflater.setInput(data, -1, 2);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative offset");
        }
    }

    static void testSetInputNegativeLength() {
        Inflater inflater = new Inflater();
        byte[] data = {1, 2, 3};
        boolean thrown = false;
        try {
            inflater.setInput(data, 0, -1);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative length");
        }
    }

    static void testSetInputOffsetBeyondArray() {
        Inflater inflater = new Inflater();
        byte[] data = {1, 2, 3};
        boolean thrown = false;
        try {
            inflater.setInput(data, 4, 1); // offset beyond array length
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for offset beyond array length");
        }
    }

    static void testSetInputOffsetPlusLengthBeyondArray() {
        Inflater inflater = new Inflater();
        byte[] data = {1, 2, 3};
        boolean thrown = false;
        try {
            inflater.setInput(data, 1, 3); // 1 + 3 = 4, beyond array length
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for offset + length beyond array length");
        }
    }
}
