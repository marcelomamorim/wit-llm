/*
 * @test
 * @summary Verify that position() on a jrt: SeekableByteChannel starts at zero and reports the number of bytes consumed by reads
 * @run main JrtFileSystemWitupTest
 */
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));
        Path p = fs.getPath("/java.base/java/lang/Object.class");
        try (SeekableByteChannel ch = Files.newByteChannel(p)) {
            check(ch.position() == 0L, "initial position must be 0, was " + ch.position());
            ByteBuffer buf = ByteBuffer.allocate(16);
            int n = ch.read(buf);
            check(n > 0, "first read must return bytes, was " + n);
            check(ch.position() == n, "position after first read must equal bytes read: " + ch.position() + " vs " + n);
            buf.clear();
            int m = ch.read(buf);
            check(m > 0, "second read must return bytes, was " + m);
            check(ch.position() == (long) n + m, "position after second read must be cumulative: " + ch.position());
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
