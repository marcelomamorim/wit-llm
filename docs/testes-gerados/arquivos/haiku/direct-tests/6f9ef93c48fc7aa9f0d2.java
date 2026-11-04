/*
 * @test
 * @summary Verify JrtFileSystemProvider.isHidden through the public jrt file system API
 * @run main JrtFileSystemProviderWitupTest
 */

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class JrtFileSystemProviderWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));

        Path javaBase = fs.getPath("/java.base");
        Path modules = fs.getPath("/modules");

        check(!fs.provider().isHidden(javaBase), "java.base should not be hidden");
        check(!fs.provider().isHidden(modules), "modules should not be hidden");

        check(fs.provider().isHidden(javaBase) == javaBase.getFileSystem().provider().isHidden(javaBase),
              "provider isHidden must be consistent for the same path");

        check(!Files.isHidden(javaBase), "Files.isHidden should delegate and return false for java.base");

        boolean first = fs.provider().isHidden(modules);
        boolean second = fs.provider().isHidden(modules);
        check(first == second, "isHidden must be deterministic");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
