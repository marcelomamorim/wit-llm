/*
 * @test
 * @summary WITUP-derived checks for JrtPath.register(WatchService, Kind...) argument validation and unsupported registration
 * @run main JrtPathWitupTest
 */

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

public class JrtPathWitupTest {

    static final class NoopWatchService implements WatchService {
        @Override
        public void close() throws IOException {
        }

        @Override
        public WatchKey poll() {
            return null;
        }

        @Override
        public WatchKey poll(long timeout, TimeUnit unit) {
            return null;
        }

        @Override
        public WatchKey take() {
            return null;
        }
    }

    interface Action {
        void run() throws Exception;
    }

    static void expectNPE(String name, Action a) {
        try {
            a.run();
        } catch (NullPointerException e) {
            return;
        } catch (Exception e) {
            throw new RuntimeException(name + ": unexpected " + e, e);
        }
        throw new RuntimeException(name + ": expected NullPointerException");
    }

    static void expectUOE(String name, Action a) {
        try {
            a.run();
        } catch (UnsupportedOperationException e) {
            return;
        } catch (Exception e) {
            throw new RuntimeException(name + ": unexpected " + e, e);
        }
        throw new RuntimeException(name + ": expected UnsupportedOperationException");
    }

    public static void main(String[] args) throws Exception {
        FileSystem jrt = FileSystems.getFileSystem(URI.create("jrt:/"));
        final Path p = jrt.getPath("/modules/java.base");
        final WatchService ws = new NoopWatchService();

        expectNPE("nullWatcher", () -> p.register(null, StandardWatchEventKinds.ENTRY_CREATE));

        expectNPE("nullEvents", () -> p.register(ws, (WatchEvent.Kind<?>[]) null));

        expectUOE("validArgumentsUnsupported", () -> p.register(ws, StandardWatchEventKinds.ENTRY_CREATE));
    }
}
