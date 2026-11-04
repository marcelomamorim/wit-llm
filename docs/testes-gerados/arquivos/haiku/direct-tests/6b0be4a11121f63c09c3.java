/*
 * @test
 * @summary Checks JrtPath.register(WatchService, WatchEvent.Kind...) through the public Path API
 * @run main JrtPathWitupTest
 */

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class JrtPathWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem jrt = FileSystems.getFileSystem(URI.create("jrt:/"));
        Path p = jrt.getPath("/java.base");

        try (WatchService ws = FileSystems.getDefault().newWatchService()) {
            try {
                WatchKey key = p.register(ws, StandardWatchEventKinds.ENTRY_CREATE);
                if (key == null) {
                    throw new AssertionError("register returned null WatchKey");
                }
                key.cancel();
            } catch (UnsupportedOperationException expected) {
                // jrt file system does not support watching; this is an accepted outcome
            }
        }
    }
}
