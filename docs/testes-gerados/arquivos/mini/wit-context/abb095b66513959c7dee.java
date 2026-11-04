/*
 * @test
 * @summary Test PipedOutputStream.write(int) for expected IOException scenarios
 * @run main PipedOutputStreamWitupTest
 */

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteThrowsWhenSinkIsNull();
        testWriteThrowsWhenSinkNotConnected();
        testWriteThrowsWhenSinkClosed();
    }

    // Test that write(int) throws IOException if sink is null (not connected)
    private static void testWriteThrowsWhenSinkIsNull() {
        PipedOutputStream pos = new PipedOutputStream(); // not connected
        try {
            pos.write(0);
            throw new RuntimeException("Expected IOException not thrown when sink is null");
        } catch (IOException e) {
            // Expected
            if (!"Pipe not connected".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    // Test that write(int) throws IOException if sink is connected but not properly connected internally
    private static void testWriteThrowsWhenSinkNotConnected() throws IOException {
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream();
        // Connect pos to pis
        pos.connect(pis);

        // Close pis to simulate closed state that causes IOException on receive
        pis.close();

        try {
            pos.write(1);
            throw new RuntimeException("Expected IOException not thrown when sink is closed");
        } catch (IOException e) {
            // Expected IOException due to closed pipe
        }
    }

    // Test that write(int) throws IOException if sink is connected but closed by reader or writer
    private static void testWriteThrowsWhenSinkClosed() throws IOException {
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream(pis);

        // Close the PipedInputStream to simulate closedByReader
        pis.close();

        try {
            pos.write(2);
            throw new RuntimeException("Expected IOException not thrown when sink closed by reader");
        } catch (IOException e) {
            // Expected
        }
    }
}
