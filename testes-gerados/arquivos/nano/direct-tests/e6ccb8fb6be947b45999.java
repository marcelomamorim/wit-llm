/* @test    @summary Test PipedOutputStream constructor with connected PipedInputStream    @run main PipedOutputStreamWitupTest */

import java.io.*;

public class PipedOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream(pis);
        // Write data to the output stream
        String message = "test";
        pos.write(message.getBytes());
        pos.close();
        // Read data from the input stream
        byte[] buffer = new byte[message.length()];
        int readBytes = pis.read(buffer);
        String received = new String(buffer, 0, readBytes);
        assert received.equals(message) : "Data mismatch: expected '" + message + "', got '" + received + "'";
        pis.close();
    }
}
