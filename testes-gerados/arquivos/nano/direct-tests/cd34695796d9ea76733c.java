/*
 * @test    @summary Test PipedReader constructor with PipedWriter and pipe size
 * @run main PipedReaderWitupTest
 */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedReaderWitupTest {
    public static void main(String[] args) throws IOException {
        testConstructorWithValidInputs();
        testConstructorWithZeroPipeSize();
        testConstructorWithNegativePipeSize();
    }

    static void testConstructorWithValidInputs() throws IOException {
        PipedWriter writer = new PipedWriter();
        int pipeSize = 2048;
        PipedReader reader = new PipedReader(writer, pipeSize);
        assert reader != null : "Reader should not be null";
    }

    static void testConstructorWithZeroPipeSize() {
        PipedWriter writer = new PipedWriter();
        try {
            new PipedReader(writer, 0);
            assert false : "Expected IllegalArgumentException for zero pipe size";
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            assert false : "Unexpected IOException";
        }
    }

    static void testConstructorWithNegativePipeSize() {
        PipedWriter writer = new PipedWriter();
        try {
            new PipedReader(writer, -10);
            assert false : "Expected IllegalArgumentException for negative pipe size";
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            assert false : "Unexpected IOException";
        }
    }
}
