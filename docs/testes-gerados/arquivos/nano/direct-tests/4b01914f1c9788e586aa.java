/* @test    @summary Test PipedReader constructor with valid pipe size    @run main PipedReaderWitupTest */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedReaderWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidSize();
        testConstructorWithZeroSize();
        testConstructorWithNegativeSize();
    }

    static void testConstructorWithValidSize() {
        try {
            int validSize = 1024;
            PipedReader reader = new PipedReader(validSize);
            assert reader != null : "PipedReader should be instantiated with valid size";
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for valid size", e);
        }
    }

    static void testConstructorWithZeroSize() {
        try {
            new PipedReader(0);
            throw new AssertionError("Expected IllegalArgumentException for zero size")
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for zero size", e);
        }
    }

    static void testConstructorWithNegativeSize() {
        try {
            new PipedReader(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative size")
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for negative size", e);
        }
    }
}
