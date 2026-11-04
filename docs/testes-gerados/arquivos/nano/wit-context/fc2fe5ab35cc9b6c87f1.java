/* @test    @summary Test for PushbackReader constructor with invalid size argument    @run main PushbackReaderWitupTest */

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class PushbackReaderWitupTest {

    public static void main(String[] args) {
        testConstructorWithZeroSize();
        testConstructorWithNegativeSize();
        testConstructorWithValidSize();
    }

    private static void testConstructorWithZeroSize() {
        try {
            Reader reader = new StringReader("");
            new java.io.PushbackReader(reader, 0);
            System.err.println("Failed: Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown for size=0");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for size=0: " + e);
        }
    }

    private static void testConstructorWithNegativeSize() {
        try {
            Reader reader = new StringReader("");
            new java.io.PushbackReader(reader, -5);
            System.err.println("Failed: Expected IllegalArgumentException for size=-5");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown for size=-5");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for size=-5: " + e);
        }
    }

    private static void testConstructorWithValidSize() {
        try {
            Reader reader = new StringReader("");
            java.io.PushbackReader pbr = new java.io.PushbackReader(reader, 10);
            if (pbr != null) {
                System.out.println("Passed: PushbackReader created with size=10");
            } else {
                System.err.println("Failed: PushbackReader is null for size=10");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for size=10: " + e);
        }
    }
}
