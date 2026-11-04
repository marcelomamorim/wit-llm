/*
 * @test
 * @summary Test for PipedWriter.flush() method, covering exception path when sink is closed.
 * @run main PipedWriterWitupTest
 */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedWriterWitupTest {

    public static void main(String[] args) {
        testFlushWhenSinkClosed();
        testFlushWhenSinkNull();
    }

    private static void testFlushWhenSinkClosed() {
        PipedReader sink = new PipedReader();
        PipedWriter writer = new PipedWriter();
        try {
            writer.connect(sink);
            sink.close(); // Simulate sink being closed by reader
            writer.flush();
            System.out.println("FAIL: Expected IOException not thrown when sink is closed.");
        } catch (IOException e) {
            if ("Pipe closed".equals(e.getMessage())) {
                System.out.println("PASS: IOException thrown as expected when sink is closed.");
            } else {
                System.out.println("FAIL: Unexpected IOException message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }

    private static void testFlushWhenSinkNull() {
        PipedWriter writer = new PipedWriter();
        // Not connecting sink, so sink remains null
        try {
            writer.flush();
            System.out.println("PASS: No exception thrown when sink is null.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception when sink is null: " + e);
        }
    }
}
