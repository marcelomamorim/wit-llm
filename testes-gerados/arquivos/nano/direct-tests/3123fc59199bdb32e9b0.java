/*
 * @test
 * @summary Test that JrtFileSystem.write(ByteBuffer) throws NonWritableChannelException
 * @run main JrtFileSystemWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.NonWritableChannelException;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) {
        testWriteThrowsException();
    }

    public static void testWriteThrowsException() {
        class DummyJrtFileSystem extends jdk.internal.jrtfs.JrtFileSystem {
            public DummyJrtFileSystem() throws IOException {
                super(null, null);
            }
            @Override
            public int write(ByteBuffer src) throws IOException {
                throw new NonWritableChannelException();
            }
        }
        try {
            DummyJrtFileSystem fs = new DummyJrtFileSystem();
            ByteBuffer buffer = ByteBuffer.allocate(10);
            boolean exceptionThrown = false;
            try {
                fs.write(buffer);
            } catch (NonWritableChannelException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected NonWritableChannelException was not thrown.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
