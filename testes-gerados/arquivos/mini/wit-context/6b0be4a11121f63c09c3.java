/*
 * @test
 * @summary Test jdk.internal.jrtfs.JrtPath.register(WatchService, WatchEvent.Kind<?>...)
 * @run main JrtPathWitupTest
 */

import java.nio.file.WatchService;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.Objects;

public class JrtPathWitupTest {

    static class DummyJrtPath extends jdk.internal.jrtfs.JrtPath {
        DummyJrtPath() {
            super(null, "");
        }

        // Expose register(WatchService, WatchEvent.Kind<?>..., WatchEvent.Modifier...) for testing
        public WatchKey testRegister(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier[] modifiers) {
            // Call the actual register method with modifiers
            return super.register(watcher, events, modifiers);
        }
    }

    static class DummyWatchService implements WatchService {
        @Override
        public void close() {
        }

        @Override
        public WatchKey poll() {
            return null;
        }

        @Override
        public WatchKey poll(long timeout, java.util.concurrent.TimeUnit unit) {
            return null;
        }

        @Override
        public WatchKey take() {
            return null;
        }
    }

    public static void main(String[] args) {
        DummyJrtPath path = new DummyJrtPath();

        // Test 1: watcher == null -> expect NullPointerException
        try {
            path.testRegister(null, new WatchEvent.Kind<?>[0], new WatchEvent.Modifier[0]);
            throw new RuntimeException("Expected NullPointerException for null watcher");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 2: events == null and watcher != null -> expect NullPointerException
        try {
            path.testRegister(new DummyWatchService(), null, new WatchEvent.Modifier[0]);
            throw new RuntimeException("Expected NullPointerException for null events");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 3: modifiers == null and events != null and watcher != null -> expect NullPointerException
        try {
            path.testRegister(new DummyWatchService(), new WatchEvent.Kind<?>[0], null);
            throw new RuntimeException("Expected NullPointerException for null modifiers");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 4: watcher != null, events != null, modifiers != null -> expect UnsupportedOperationException
        try {
            path.testRegister(new DummyWatchService(), new WatchEvent.Kind<?>[0], new WatchEvent.Modifier[0]);
            throw new RuntimeException("Expected UnsupportedOperationException for modifiers present");
        } catch (UnsupportedOperationException uoe) {
            // expected
        }
    }
}
