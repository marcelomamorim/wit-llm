/*
 * @test
 * @summary Verify that JrtPath.toFile() throws UnsupportedOperationException through the public jrt file system API
 * @run main JrtPathWitupTest
 */

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class JrtPathWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));

        String[] absolute = {
            "/",
            "/java.base",
            "/java.base/java/lang/Object.class"
        };
        for (String s : absolute) {
            checkToFileThrows(fs.getPath(s));
        }

        checkToFileThrows(fs.getPath("java.base"));
    }

    private static void checkToFileThrows(Path p) {
        try {
            p.toFile();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("toFile() did not throw UnsupportedOperationException for " + p);
    }
}
