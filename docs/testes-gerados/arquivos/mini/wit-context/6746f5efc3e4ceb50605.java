/*
 * @test
 * @summary Test java.io.PipedWriter.write(int) for expected exceptions and normal behavior
 * @run main PipedWriterWitupTest
 */

import java.io.PipedWriter;
import java.io.PipedReader;
import java.io.IOException;

public class PipedWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteThrowsWhenNotConnected();
        testWriteThrowsWhenPipeClosedByReader();
        testWriteThrowsWhenPipeNotConnectedButSinkSet();
        testWriteSucceedsWhenConnected();
    }

    private static void testWriteThrowsWhenNotConnected() {
        PipedWriter pw = new PipedWriter();
        try {
            pw.write('a');
            throw new RuntimeException("Expected IOException not thrown when sink is null");
        } catch (IOException e) {
            if (!"Pipe not connected".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    private static void testWriteThrowsWhenPipeClosedByReader() throws IOException {
        PipedReader pr = new PipedReader();
        PipedWriter pw = new PipedWriter(pr);
        // Close the reader to simulate closedByReader
        pr.close();
        try {
            pw.write('b');
            throw new RuntimeException("Expected IOException not thrown when pipe closed by reader");
        } catch (IOException e) {
            // IOException expected, message may vary
        }
    }

    private static void testWriteThrowsWhenPipeNotConnectedButSinkSet() throws IOException {
        // Create a PipedReader but do not connect it properly
        PipedReader pr = new PipedReader();
        PipedWriter pw = new PipedWriter();

        // Use reflection to forcibly set the private sink field to pr to simulate inconsistent state
        // Reflection is necessary because sink is private and no setter is available
        // This is justified to test the !connected && sink != null condition
        try {
            java.lang.reflect.Field sinkField = PipedWriter.class.getDeclaredField("sink");
            sinkField.setAccessible(true);
            sinkField.set(pw, pr);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Reflection failed", e);
        }

        try {
            pw.write('c');
            throw new RuntimeException("Expected IOException not thrown when !connected but sink != null");
        } catch (IOException e) {
            // IOException expected
        }
    }

    private static void testWriteSucceedsWhenConnected() throws IOException {
        PipedReader pr = new PipedReader();
        PipedWriter pw = new PipedWriter(pr);

        // Write a character and verify it is received by the reader
        // Use a separate thread to read the character
        final int expected = 'x';
        final int[] received = new int[1];
        Thread readerThread = new Thread(new Runnable() {
            public void run() {
                try {
                    received[0] = pr.read();
                } catch (IOException e) {
                    received[0] = -1;
                }
            }
        });
        readerThread.start();

        pw.write(expected);

        // Wait for reader thread to finish
        try {
            readerThread.join(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (received[0] != expected) {
            throw new RuntimeException("Character written was not received correctly. Expected: " + expected + " but got: " + received[0]);
        }

        pw.close();
        pr.close();
    }
}
