/*
 * @test
 * @summary Test sun.net.www.http.ChunkedOutputStream write(int) method
 * @run main ChunkedOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import sun.net.www.http.ChunkedOutputStream;

public class ChunkedOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteSingleByte();
        testWriteMultipleBytes();
    }

    private static void testWriteSingleByte() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        ChunkedOutputStream cos = new ChunkedOutputStream(ps, 16); // small chunk size for test

        // Write single byte 0x41 ('A')
        cos.write(0x41);
        cos.flush();
        cos.close();

        byte[] result = baos.toByteArray();

        // The output should contain the chunk header, the byte 'A', and chunk footer
        // The chunk header is hex length of data (1) + CRLF, so "1\r\n"
        // Then the byte 'A' (0x41), then CRLF, then zero-length chunk "0\r\n\r\n"

        String output = new String(result, "US-ASCII");

        if (!output.contains("1\r\nA\r\n")) {
            throw new RuntimeException("Output does not contain expected chunked data for single byte write: " + output);
        }
        if (!output.endsWith("0\r\n\r\n")) {
            throw new RuntimeException("Output does not end with zero-length chunk indicating end of stream: " + output);
        }
    }

    private static void testWriteMultipleBytes() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        ChunkedOutputStream cos = new ChunkedOutputStream(ps, 8); // small chunk size for test

        // Write multiple bytes via write(int) calls
        byte[] data = {0x41, 0x42, 0x43, 0x44}; // 'A','B','C','D'
        for (byte b : data) {
            cos.write(b);
        }
        cos.flush();
        cos.close();

        byte[] result = baos.toByteArray();
        String output = new String(result, "US-ASCII");

        // The output should contain one chunk with length 4 ("4\r\n"), data "ABCD", CRLF, and zero-length chunk
        if (!output.contains("4\r\nABCD\r\n")) {
            throw new RuntimeException("Output does not contain expected chunked data for multiple byte writes: " + output);
        }
        if (!output.endsWith("0\r\n\r\n")) {
            throw new RuntimeException("Output does not end with zero-length chunk indicating end of stream: " + output);
        }
    }
}
