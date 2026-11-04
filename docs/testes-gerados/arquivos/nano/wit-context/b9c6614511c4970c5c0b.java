/* @test    @summary Test for StringWriter constructor with initialSize parameter.
 * @run main StringWriterWitupTest
 */

import java.io.StringWriter;

public class StringWriterWitupTest {

    public static void main(String[] args) {
        testConstructorNegativeSize();
        testConstructorZeroSize();
        testConstructorPositiveSize();
    }

    private static void testConstructorNegativeSize() {
        try {
            new StringWriter(-1);
            System.err.println("Failed: Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Passed: Negative size throws IllegalArgumentException");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for negative size: " + t);
        }
    }

    private static void testConstructorZeroSize() {
        try {
            StringWriter sw = new StringWriter(0);
            if (sw != null) {
                System.out.println("Passed: Zero size constructor creates StringWriter");
            } else {
                System.err.println("Failed: StringWriter is null for zero size");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for zero size: " + t);
        }
    }

    private static void testConstructorPositiveSize() {
        try {
            StringWriter sw = new StringWriter(10);
            if (sw != null) {
                System.out.println("Passed: Positive size constructor creates StringWriter");
            } else {
                System.err.println("Failed: StringWriter is null for positive size");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for positive size: " + t);
        }
    }
}
