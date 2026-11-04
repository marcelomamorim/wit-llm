/*
 * @test
 * @summary Paths.get(URI) converts a file URI to a Path and rejects URIs without a scheme or with an unknown scheme
 * @run main PathsWitupTest
 */

import java.net.URI;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathsWitupTest {
    public static void main(String[] args) throws Exception {
        URI uri = new URI("file:///tmp/paths-witup/a.txt");
        Path p = Paths.get(uri);
        if (p == null) {
            throw new RuntimeException("Paths.get(URI) returned null");
        }
        if (!"a.txt".equals(p.getFileName().toString())) {
            throw new RuntimeException("Unexpected file name: " + p.getFileName());
        }

        try {
            Paths.get(new URI("a/b.txt"));
            throw new RuntimeException("Expected IllegalArgumentException for URI without scheme");
        } catch (IllegalArgumentException expected) {
        }

        try {
            Paths.get(new URI("paths-witup-no-such-scheme:///x"));
            throw new RuntimeException("Expected FileSystemNotFoundException for unknown scheme");
        } catch (FileSystemNotFoundException expected) {
        }
    }
}
