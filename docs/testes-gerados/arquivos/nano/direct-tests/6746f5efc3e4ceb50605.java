/* @test    @summary Test PipedWriter.write(int c) method for normal operation and exception handling.    @run main PipedWriterWitupTest */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedWriterWitupTest {

    public static void main(String[] args) {
        testWriteWithoutConnection();
        testWriteWithConnection();
    }

    static void testWriteWithoutConnection() {
        PipedWriter writer = new PipedWriter();
        try {
            writer.write(65); // ASCII 'A'
            // If no exception, test fails
            System.err.println("FAIL: Expected IOException when writing without connection");
        } catch (IOException e) {
            if ("Pipe not connected".equals(e.getMessage())) {
                System.out.println("PASS: Correct exception thrown when writing without connection");
            } else {
                System.err.println("FAIL: Unexpected exception message: " + e.getMessage());
            }
        }
    }

    static void testWriteWithConnection() {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter();
        try {
            writer.connect(reader);
            writer.write(66); // ASCII 'B'
            int received = reader.read();
            if (received == 66) {
                System.out.println("PASS: Data written and read correctly");
            } else {
                System.err.println("FAIL: Data mismatch, expected 66, got " + received);
            }
        } catch (IOException e) {
            System.err.println("FAIL: Unexpected IOException: " + e.getMessage());
        }
    }
}
