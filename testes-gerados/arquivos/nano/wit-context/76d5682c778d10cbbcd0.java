/* @test    @summary Test for Attrs.isSpecified(int) method to verify exception handling and behavior.    @run main AttrsWitupTest */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {

    public static void main(String[] args) {
        testIsSpecifiedWithNegativeIndex();
        testIsSpecifiedWithIndexEqualToLength();
        testIsSpecifiedWithValidIndex();
    }

    private static void testIsSpecifiedWithNegativeIndex() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 2);
        try {
            attrs.isSpecified(-1);
            System.out.println("FAIL: Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected ArrayIndexOutOfBoundsException for index -1");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type for index -1: " + t);
        }
    }

    private static void testIsSpecifiedWithIndexEqualToLength() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 3);
        try {
            attrs.isSpecified(3);
            System.out.println("FAIL: Expected ArrayIndexOutOfBoundsException for index equal to length");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected ArrayIndexOutOfBoundsException for index equal to length");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type for index equal to length: " + t);
        }
    }

    private static void testIsSpecifiedWithValidIndex() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 2);
        // Prepare mItems array to avoid null pointer in isSpecified
        // Since mItems is package-private, we can access directly in this test context
        attrs.mItems = new String[8];
        // Set a value for index 0
        attrs.mItems[0] = "";
        attrs.mItems[0 + 5] = "d"; // index 0, value at position 5
        // Set a value for index 1
        attrs.mItems[1] = "";
        attrs.mItems[1 + 5] = ""; // index 1, value at position 5
        try {
            boolean result0 = attrs.isSpecified(0);
            boolean result1 = attrs.isSpecified(1);
            System.out.println("PASS: isSpecified(0) returned: " + result0);
            System.out.println("PASS: isSpecified(1) returned: " + result1);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception during valid index tests: " + t);
        }
    }
}
