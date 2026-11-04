/*
 * @test
 * @summary Verifies that position() of a jrt:/ file channel starts at zero, advances by the number of bytes read, and reaches size() at end of file
 * @run main JrtFileSystemWitupTest
 */

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));
        Path p = fs.getPath("/modules/java.base/java/lang/Object.class");
        try (SeekableByteChannel ch = Files.newByteChannel(p, StandardOpenOption.READ)) {
            if (ch.position() != 0) {
                throw new AssertionError("initial position expected 0 but was " + ch.position());
            }
            long size = ch.size();
            if (size <= 0) {
                throw new AssertionError("unexpected size: " + size);
            }

            ByteBuffer first = ByteBuffer.allocate(16);
            int n = ch.read(first);
            if (n <= 0) {
                throw new AssertionError("first read returned " + n);
            }
            if (ch.position() != n) {
                throw new AssertionError("position after first read expected " + n + " but was " + ch.position());
            }

            long total = n;
            ByteBuffer buf = ByteBuffer.allocate(4096);
            while (true) {
                buf.clear();
                int r = ch.read(buf);
                if (r < 0) {
                    break;
                }
                total += r;
                if (ch.position() != total) {
                    throw new AssertionError("position expected " + total + " but was " + ch.position());
                }
            }

            if (ch.position() != size) {
                throw new AssertionError("position at EOF expected " + size + " but was " + ch.position());
            }
            if (total != size) {
                throw new AssertionError("total bytes read expected " + size + " but was " + total);
            }
        }
    }
}
