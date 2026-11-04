/*
 * @test
 * @summary Test for java.util.zip.Inflater.setInput(byte[], int, int)
 * @run main InflaterWitupTest
 */

import java.util.zip.Inflater;

public class InflaterWitupTest {

    public static void main(String[] args) {
        testSetInputWithValidParameters();
        testSetInputWithNegativeOffset();
        testSetInputWithNegativeLength();
        testSetInputWithOffsetExceedingArray();
    }

    private static void testSetInputWithValidParameters() {
        try {
            Inflater inflater = new Inflater();
            byte[] input = {1, 2, 3, 4, 5};
            inflater.setInput(input, 1, 3);
            System.out.println("testSetInputWithValidParameters: PASSED");
        } catch (Exception e) {
            System.out.println("testSetInputWithValidParameters: FAILED - " + e);
        }
    }

    private static void testSetInputWithNegativeOffset() {
        try {
            Inflater inflater = new Inflater();
            byte[] input = {1, 2, 3};
            inflater.setInput(input, -1, 2);
            System.out.println("testSetInputWithNegativeOffset: FAILED - no exception");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("testSetInputWithNegativeOffset: PASSED");
        } catch (Exception e) {
            System.out.println("testSetInputWithNegativeOffset: FAILED - " + e);
        }
    }

    private static void testSetInputWithNegativeLength() {
        try {
            Inflater inflater = new Inflater();
            byte[] input = {1, 2, 3};
            inflater.setInput(input, 0, -1);
            System.out.println("testSetInputWithNegativeLength: FAILED - no exception");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("testSetInputWithNegativeLength: PASSED");
        } catch (Exception e) {
            System.out.println("testSetInputWithNegativeLength: FAILED - " + e);
        }
    }

    private static void testSetInputWithOffsetExceedingArray() {
        try {
            Inflater inflater = new Inflater();
            byte[] input = {1, 2, 3};
            inflater.setInput(input, 2, 2); // offset + len = 4 > input.length
            System.out.println("testSetInputWithOffsetExceedingArray: FAILED - no exception");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("testSetInputWithOffsetExceedingArray: PASSED");
        } catch (Exception e) {
            System.out.println("testSetInputWithOffsetExceedingArray: FAILED - " + e);
        }
    }
}
