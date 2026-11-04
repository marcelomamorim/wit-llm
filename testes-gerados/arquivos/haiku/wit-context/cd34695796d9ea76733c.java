/*
 * @test
 * @summary Witup generated checks for PipedReader(PipedWriter, int) pipe size validation and basic connected data flow
 * @run main PipedReaderWitupTest
 */

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipedReaderWitupTest {

    public static void main(String[] args) throws Exception {
        expectIllegalArgument(0);
        expectIllegalArgument(-1);
        expectIllegalArgument(Integer.MIN_VALUE);
        testConnectedDataFlow();
    }

    static void expectIllegalArgument(int pipeSize) throws IOException {
        PipedWriter writer = new PipedWriter();
        try {
            new PipedReader(writer, pipeSize);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException for pipeSize=" + pipeSize);
    }

    static void testConnectedDataFlow() throws IOException {
        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader(writer, 16);
        try {
            writer.write('A');
            int c = reader.read();
            if (c != 'A') {
                throw new AssertionError("Expected 'A' but got " + c);
            }
        } finally {
            writer.close();
            reader.close();
        }
    }
}
