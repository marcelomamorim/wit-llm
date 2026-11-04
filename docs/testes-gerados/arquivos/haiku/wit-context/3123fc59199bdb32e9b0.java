/*
 * @test
 * @summary Verifies that a read-only jrt channel rejects write(ByteBuffer) with NonWritableChannelException
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
            ByteBuffer empty = ByteBuffer.allocate(0);
            expectNonWritable(ch, empty);
            ByteBuffer data = ByteBuffer.wrap(new byte[] {1, 2, 3, 4});
            expectNonWritable(ch, data);
        }
    }

    private static void expectNonWritable(SeekableByteChannel ch, ByteBuffer buf) throws Exception {
        try {
            ch.write(buf);
        } catch (NonWritableChannelException expected) {
            return;
        }
        throw new AssertionError("expected NonWritableChannelException");
    }
}
