/*
 * @test
 * @summary Verifies Paths.get(URI) delegates to Path.of(URI) and rejects unsupported URIs
 * @run main PathsWitupTest
 */

import java.net.URI;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathsWitupTest {
    public static void main(String[] args) throws Exception {
        URI uri = URI.create("file:///tmp/witup-paths/a.txt");
        Path p = Paths.get(uri);
        check(p != null, "Paths.get(URI) returned null");
        check(p.equals(Path.of(uri)), "Paths.get(URI) differs from Path.of(URI)");
        check("a.txt".equals(p.getFileName().toString()), "unexpected file name");
        check(Paths.get(uri).equals(p), "repeated call returned a different path");

        boolean iae = false;
        try {
            Paths.get(URI.create("relative/path"));
        } catch (IllegalArgumentException e) {
            iae = true;
        }
        check(iae, "relative URI did not throw IllegalArgumentException");

        boolean fsnf = false;
        try {
            Paths.get(URI.create("witupnoscheme:///x"));
        } catch (FileSystemNotFoundException e) {
            fsnf = true;
        }
        check(fsnf, "unknown scheme did not throw FileSystemNotFoundException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
