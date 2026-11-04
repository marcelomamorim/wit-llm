/*
 * @test
 * @summary Test java.util.zip.Inflater.setInput(byte[], int, int) for valid and invalid inputs
 * @run main InflaterWitupTest
 */

import java.util.zip.Inflater;

public class InflaterWitupTest {
    public static void main(String[] args) {
        testSetInputValid();
        testSetInputInvalidNegativeOffset();
        testSetInputInvalidNegativeLength();
        testSetInputInvalidOffsetLengthOverflow();
    }

    private static void testSetInputValid() {
        Inflater inflater = new Inflater();
        byte[] input = new byte[10];
        try {
            inflater.setInput(input, 0, 10); // full array
            inflater.setInput(input, 5, 5);  // partial array
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid input", e);
        }
    }

    private static void testSetInputInvalidNegativeOffset() {
        Inflater inflater = new Inflater();
        byte[] input = new byte[10];
        try {
            inflater.setInput(input, -1, 5);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative offset");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type for negative offset", e);
        }
    }

    private static void testSetInputInvalidNegativeLength() {
        Inflater inflater = new Inflater();
        byte[] input = new byte[10];
        try {
            inflater.setInput(input, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type for negative length", e);
        }
    }

    private static void testSetInputInvalidOffsetLengthOverflow() {
        Inflater inflater = new Inflater();
        byte[] input = new byte[10];
        try {
            inflater.setInput(input, 6, 5); // 6 + 5 = 11 > input.length (10)
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for offset+length overflow");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type for offset+length overflow", e);
        }
    }
}
