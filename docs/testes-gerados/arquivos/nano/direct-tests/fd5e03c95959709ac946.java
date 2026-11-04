/* @test    @summary Test default constructor of PipedInputStream    @run main PipedInputStreamWitupTest */

import java.io.PipedInputStream;

public class PipedInputStreamWitupTest {
    public static void main(String[] args) {
        PipedInputStream pis = new PipedInputStream();
        if (pis == null) {
            throw new AssertionError("PipedInputStream instance is null");
        }
        // Verify default pipe size (internal buffer size)
        // Since buffer is protected, we cannot access directly; test instantiation only.
        System.out.println("PipedInputStream default constructor test passed.");
    }
}
