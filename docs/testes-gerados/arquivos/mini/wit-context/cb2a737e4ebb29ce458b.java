/*
 * @test
 * @summary Deterministic tests for sun.net.www.http.ChunkedOutputStream.write(int)
 * @run main sun.net.www.http.ChunkedOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import sun.net.www.http.ChunkedOutputStream;

public class ChunkedOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteSingleByte();
    }

    private static void testWriteSingleByte() throws Exception {
        // Setup a ByteArrayOutputStream to capture output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        // Create ChunkedOutputStream with default chunk size
        ChunkedOutputStream cos = new ChunkedOutputStream(ps);

        // Write a single byte (value 0x41 = 'A')
        cos.write(0x41);
        cos.flush();

        // The output should contain the chunk header, the byte, and the footer
        byte[] output = baos.toByteArray();

        // Basic checks on output length and content
        if (output.length < 5) {
            throw new RuntimeException("Output too short, chunk encoding likely broken");
        }

        // The first byte(s) should be ASCII hex digits for chunk size (1 byte data -> "1\r\n")
        // So output starts with '1', '\r', '\n'
        if (output[0] != '1' || output[1] != '\r' || output[2] != '\n') {
            throw new RuntimeException("Chunk header incorrect");
        }

        // The data byte should be 'A' (0x41)
        if (output[3] != 0x41) {
            throw new RuntimeException("Chunk data byte incorrect");
        }

        // The footer should be '\r\n'
        if (output[4] != '\r' || output[5] != '\n') {
            throw new RuntimeException("Chunk footer incorrect");
        }

        // Close the stream to write the terminating chunk
        cos.close();

        // After close, output should end with "0\r\n\r\n"
        byte[] closedOutput = baos.toByteArray();
        int len = closedOutput.length;
        if (len < 5) {
            throw new RuntimeException("Output too short after close");
        }

        // Check last 5 bytes for terminating chunk
        // "0\r\n\r\n" in ASCII
        if (closedOutput[len - 5] != '0' || closedOutput[len - 4] != '\r' || closedOutput[len - 3] != '\n' ||
            closedOutput[len - 2] != '\r' || closedOutput[len - 1] != '\n') {
            throw new RuntimeException("Terminating chunk missing or incorrect after close");
        }
    }
}
