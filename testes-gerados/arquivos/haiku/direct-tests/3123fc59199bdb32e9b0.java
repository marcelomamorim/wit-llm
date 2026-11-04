/*
 * @test
 * @summary Verify that writing to a read-only jrt file channel throws NonWritableChannelException
 * @run main JrtFileSystemWitupTest
 */

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));
        Path p = fs.getPath("/modules/java.base/java/lang/Object.class");
        try (SeekableByteChannel ch = Files.newByteChannel(p)) {
            boolean thrown = false;
            try {
                ch.write(ByteBuffer.allocate(1));
            } catch (NonWritableChannelException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected NonWritableChannelException");
            }
        }
    }
}
