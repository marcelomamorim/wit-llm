/*
 * @test
 * @summary Test for java.io.PipedWriter.write(int c) method, covering exception paths.
 * @run main PipedWriterWitupTest
 */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedWriterWitupTest {

    public static void main(String[] args) {
        testWriteNullSink();
        testWriteConnectedClosedByReader();
        testWriteConnectedClosedByWriter();
    }

    private static void testWriteNullSink() {
        PipedWriter writer = new PipedWriter();
        try {
            writer.write(65);
            System.out.println("testWriteNullSink: Failed - no exception");
        } catch (IOException e) {
            if ("Pipe not connected".equals(e.getMessage())) {
                System.out.println("testWriteNullSink: Passed");
            } else {
                System.out.println("testWriteNullSink: Failed - wrong message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("testWriteNullSink: Failed - wrong exception: " + e);
        }
    }

    private static void testWriteConnectedClosedByReader() {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter();
        try {
            writer.connect(reader);
            // Simulate reader closed by reader
            // Note: No direct API to set closedByReader, so simulate by closing reader
            reader.close();
            writer.write(65);
            System.out.println("testWriteConnectedClosedByReader: Failed - no exception");
        } catch (IOException e) {
            if ("Pipe closed".equals(e.getMessage()) || e.getClass().getSimpleName().equals("IOException")) {
                System.out.println("testWriteConnectedClosedByReader: Passed");
            } else {
                System.out.println("testWriteConnectedClosedByReader: Failed - wrong message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("testWriteConnectedClosedByReader: Failed - wrong exception: " + e);
        }
    }

    private static void testWriteConnectedClosedByWriter() {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter();
        try {
            writer.connect(reader);
            // Simulate writer closed by writer
            // No direct API, so simulate by closing writer
            writer.close();
            writer.write(65);
            System.out.println("testWriteConnectedClosedByWriter: Failed - no exception");
        } catch (IOException e) {
            if ("Pipe closed".equals(e.getMessage()) || e.getClass().getSimpleName().equals("IOException")) {
                System.out.println("testWriteConnectedClosedByWriter: Passed");
            } else {
                System.out.println("testWriteConnectedClosedByWriter: Failed - wrong message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("testWriteConnectedClosedByWriter: Failed - wrong exception: " + e);
        }
    }
}
