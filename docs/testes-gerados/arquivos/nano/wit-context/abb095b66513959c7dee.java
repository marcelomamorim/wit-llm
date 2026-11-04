/*
 * @test
 * @summary Test PipedOutputStream.write(int b) for exception paths
 * @run main PipedOutputStreamWitupTest
 */

import java.io.*;

public class PipedOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteNullSink();
        testWriteConnectedClosedByReader();
        testWriteConnectedClosedByWriter();
    }

    private static void testWriteNullSink() {
        PipedOutputStream pos = new PipedOutputStream();
        try {
            pos.write(1);
            System.out.println("FAIL: Expected IOException for null sink");
        } catch (IOException e) {
            if ("Pipe not connected".equals(e.getMessage())) {
                System.out.println("PASS: Null sink throws expected IOException");
            } else {
                System.out.println("FAIL: Unexpected message: " + e.getMessage());
            }
        }
    }

    private static void testWriteConnectedClosedByReader() throws IOException {
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream(pis);
        // Close the sink (reader) to simulate closed by reader
        pis.close();
        try {
            pos.write(1);
            System.out.println("FAIL: Expected IOException for closed by reader");
        } catch (IOException e) {
            if ("Pipe not connected".equals(e.getMessage()) || e.getMessage().contains("closed")) {
                System.out.println("PASS: Closed by reader throws IOException");
            } else {
                System.out.println("FAIL: Unexpected message: " + e.getMessage());
            }
        }
    }

    private static void testWriteConnectedClosedByWriter() throws IOException {
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream(pis);
        // Simulate closed by writer by closing the output stream
        pos.close();
        try {
            pos.write(1);
            System.out.println("FAIL: Expected IOException for closed by writer");
        } catch (IOException e) {
            if ("Stream closed".equals(e.getMessage()) || e.getMessage().contains("closed")) {
                System.out.println("PASS: Closed by writer throws IOException");
            } else {
                System.out.println("FAIL: Unexpected message: " + e.getMessage());
            }
        }
    }
}
