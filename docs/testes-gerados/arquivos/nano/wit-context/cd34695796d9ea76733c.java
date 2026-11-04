/* @test    @summary Test constructor PipedReader(PipedWriter src, int pipeSize) with valid and invalid pipeSize values    @run main PipedReaderWitupTest */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedReaderWitupTest {

    public static void main(String[] args) {
        testValidPipeSize();
        testInvalidPipeSize();
    }

    static void testValidPipeSize() {
        try {
            PipedWriter writer = new PipedWriter();
            PipedReader reader = new PipedReader(writer, 1024);
            if (reader == null) {
                throw new AssertionError("PipedReader should not be null for valid pipeSize");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for valid pipeSize", e);
        }
    }

    static void testInvalidPipeSize() {
        try {
            PipedWriter writer = new PipedWriter();
            new PipedReader(writer, 0);
            throw new AssertionError("Expected IllegalArgumentException for pipeSize=0");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for pipeSize=0", e);
        }
        try {
            PipedWriter writer = new PipedWriter();
            new PipedReader(writer, -1);
            throw new AssertionError("Expected IllegalArgumentException for pipeSize=-1");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for pipeSize=-1", e);
        }
    }
}
