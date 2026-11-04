/*
 * @test
 * @summary Test that JrtFileSystem.write(ByteBuffer) always throws NonWritableChannelException
 * @run main jdk.internal.jrtfs.JrtFileSystemWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.NonWritableChannelException;
import jdk.internal.jrtfs.JrtFileSystem;

public class JrtFileSystemWitupTest {
    public static void main(String[] args) throws IOException {
        JrtFileSystem fs = new JrtFileSystem(null, null) {
            @Override
            public int write(ByteBuffer src) throws IOException {
                return super.write(src);
            }
        };

        ByteBuffer buffer = ByteBuffer.allocate(1);
        try {
            fs.write(buffer);
            throw new AssertionError("Expected NonWritableChannelException not thrown");
        } catch (NonWritableChannelException e) {
            // expected
        }
    }
}
