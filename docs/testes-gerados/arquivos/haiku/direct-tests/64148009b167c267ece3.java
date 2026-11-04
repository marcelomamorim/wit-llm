/*
 * @test
 * @summary Verify PipedWriter.connect(PipedReader) argument checks, state checks and data transfer
 * @run main PipedWriterWitupTest
 */

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipedWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testConnectNullThrowsNPE();
        testConnectFreshPairTransfersData();
        testConnectTwiceThrowsAlreadyConnected();
        testReaderAlreadyConnectedThrowsAlreadyConnected();
        testClosedWriterCannotConnect();
        testClosedReaderCannotConnect();
        testConstructorWithReaderConnects();
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testConnectNullThrowsNPE() {
        PipedWriter w = new PipedWriter();
        try {
            w.connect(null);
            fail("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException e) {
            fail("unexpected IOException: " + e);
        }
    }

    static void testConnectFreshPairTransfersData() throws IOException {
        PipedReader r = new PipedReader();
        PipedWriter w = new PipedWriter();
        w.connect(r);
        w.write('a');
        int c = r.read();
        if (c != 'a') {
            fail("expected 'a' but got " + c);
        }
        w.close();
        r.close();
    }

    static void testConnectTwiceThrowsAlreadyConnected() throws IOException {
        PipedWriter w = new PipedWriter();
        PipedReader r1 = new PipedReader();
        PipedReader r2 = new PipedReader();
        w.connect(r1);
        try {
            w.connect(r2);
            fail("expected IOException on second connect");
        } catch (IOException e) {
            if (!"Already connected".equals(e.getMessage())) {
                fail("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testReaderAlreadyConnectedThrowsAlreadyConnected() throws IOException {
        PipedReader r = new PipedReader();
        PipedWriter w1 = new PipedWriter();
        PipedWriter w2 = new PipedWriter();
        w1.connect(r);
        try {
            w2.connect(r);
            fail("expected IOException when reader already connected");
        } catch (IOException e) {
            if (!"Already connected".equals(e.getMessage())) {
                fail("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testClosedWriterCannotConnect() throws IOException {
        PipedWriter w = new PipedWriter();
        w.close();
        PipedReader r = new PipedReader();
        try {
            w.connect(r);
            fail("expected IOException for closed writer");
        } catch (IOException e) {
            if (!"Pipe closed".equals(e.getMessage())) {
                fail("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testClosedReaderCannotConnect() throws IOException {
        PipedReader r = new PipedReader();
        r.close();
        PipedWriter w = new PipedWriter();
        try {
            w.connect(r);
            fail("expected IOException for closed reader");
        } catch (IOException e) {
            if (!"Pipe closed".equals(e.getMessage())) {
                fail("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testConstructorWithReaderConnects() throws IOException {
        PipedReader r = new PipedReader();
        PipedWriter w = new PipedWriter(r);
        w.write('z');
        int c = r.read();
        if (c != 'z') {
            fail("expected 'z' but got " + c);
        }
        w.close();
        r.close();
    }
}
