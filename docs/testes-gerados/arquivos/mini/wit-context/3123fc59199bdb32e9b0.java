/*
 * @test
 * @summary Test that JrtFileSystem.write(ByteBuffer) always throws NonWritableChannelException
 * @run main JrtFileSystemWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.NonWritableChannelException;
import jdk.internal.jrtfs.JrtFileSystem;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a minimal subclass to instantiate JrtFileSystem since no public constructor is available
        JrtFileSystem fs = new JrtFileSystemStub();

        ByteBuffer buffer = ByteBuffer.allocate(1);

        try {
            fs.write(buffer);
            throw new AssertionError("Expected NonWritableChannelException not thrown");
        } catch (NonWritableChannelException expected) {
            // Expected exception
        }
    }

    // Minimal stub subclass to allow instantiation for testing
    static class JrtFileSystemStub extends JrtFileSystem {
        JrtFileSystemStub() {
            super(null, null);
        }

        @Override
        public int write(ByteBuffer src) throws IOException {
            return super.write(src);
        }
    }
}
