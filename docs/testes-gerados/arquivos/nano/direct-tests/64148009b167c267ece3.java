/* @test    @summary Test connect method of PipedWriter with PipedReader.    @run main PipedWriterWitupTest */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedWriterWitupTest {

    public static void main(String[] args) {
        testConnectNullReader();
        testConnectAlreadyConnected();
        testConnectClosedReader();
        testConnectNormal();
    }

    static void testConnectNullReader() {
        PipedWriter writer = new PipedWriter();
        try {
            writer.connect(null);
            System.err.println("Failed: connect(null) did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException e) {
            System.err.println("Failed: connect(null) threw IOException instead of NullPointerException");
        }
    }

    static void testConnectAlreadyConnected() {
        PipedReader reader1 = new PipedReader();
        PipedReader reader2 = new PipedReader();
        PipedWriter writer = new PipedWriter();
        try {
            writer.connect(reader1);
            // Attempt to connect again to a different reader
            writer.connect(reader2);
            System.err.println("Failed: second connect() did not throw IOException");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testConnectClosedReader() {
        PipedReader reader = new PipedReader() {
            boolean closedByReader = true;
        };
        PipedWriter writer = new PipedWriter();
        try {
            writer.connect(reader);
            System.err.println("Failed: connect() with closed reader did not throw IOException");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testConnectNormal() {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter();
        try {
            writer.connect(reader);
            if (reader.connected != true) {
                System.err.println("Failed: reader not marked as connected after connect()");
            }
            if (reader.in != -1 || reader.out != 0) {
                System.err.println("Failed: reader in/out not initialized correctly");
            }
        } catch (IOException e) {
            System.err.println("Failed: connect() threw IOException: " + e);
        }
    }
}
