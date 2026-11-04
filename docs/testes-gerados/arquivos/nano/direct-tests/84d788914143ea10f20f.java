/* @test    @summary Test PipedInputStream constructor with custom pipe size    @run main PipedInputStreamWitupTest */

import java.io.PipedInputStream;

public class PipedInputStreamWitupTest {
    public static void main(String[] args) {
        testDefaultPipeSize();
        testCustomPipeSize();
    }

    static void testDefaultPipeSize() {
        PipedInputStream pis = new PipedInputStream(1024);
        assert pis != null : "PipedInputStream should be instantiated";
        assert pis.buffer != null : "Buffer should be initialized";
        assert pis.buffer.length == 1024 : "Buffer size should be 1024";
    }

    static void testCustomPipeSize() {
        int customSize = 2048;
        PipedInputStream pis = new PipedInputStream(customSize);
        assert pis != null : "PipedInputStream should be instantiated";
        assert pis.buffer != null : "Buffer should be initialized";
        assert pis.buffer.length == customSize : "Buffer size should be " + customSize;
    }
}
