/* @test    @summary Tests for Attrs.isDeclared(int) method to verify exception handling and behavior.    @run main AttrsWitupTest */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {

    public static void main(String[] args) {
        testIsDeclaredWithNegativeIndex();
        testIsDeclaredWithIndexEqualToLength();
        testIsDeclaredWithValidIndex();
    }

    private static void testIsDeclaredWithNegativeIndex() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 2); // setting length to 2
        try {
            attrs.isDeclared(-1);
            System.out.println("FAIL: Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected ArrayIndexOutOfBoundsException for index -1");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testIsDeclaredWithIndexEqualToLength() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 2); // setting length to 2
        try {
            attrs.isDeclared(2);
            System.out.println("FAIL: Expected ArrayIndexOutOfBoundsException for index 2");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected ArrayIndexOutOfBoundsException for index 2");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testIsDeclaredWithValidIndex() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 3); // setting length to 3
        // Initialize mItems to avoid null pointer
        attrs.mItems = new String[8];
        // Set a non-null value at index 1
        attrs.mItems[1 * 8 + 5] = "declared";
        try {
            boolean result = attrs.isDeclared(1);
            if (result) {
                System.out.println("PASS: isDeclared(1) returned true as expected");
            } else {
                System.out.println("FAIL: isDeclared(1) returned false unexpectedly");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
