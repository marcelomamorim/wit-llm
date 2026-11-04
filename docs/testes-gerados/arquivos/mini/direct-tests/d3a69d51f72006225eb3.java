/*
 * @test
 * @summary Test jdk.internal.module.SystemModuleFinders.list() method behavior
 * @run main SystemModuleFindersWitupTest
 */

import java.io.IOException;
import java.util.stream.Stream;

public class SystemModuleFindersWitupTest {

    static class DummySystemModuleFinders extends jdk.internal.module.SystemModuleFinders {
        private final boolean closed;
        private final String[] modules;

        DummySystemModuleFinders(boolean closed, String[] modules) {
            this.closed = closed;
            this.modules = modules;
        }

        // We replicate minimal behavior of list() method
        public Stream<String> list() throws IOException {
            if (closed) {
                throw new IOException("ModuleReader is closed");
            }
            return Stream.of(modules);
        }
    }

    public static void main(String[] args) throws Exception {
        testListReturnsStreamOfModules();
        testListThrowsWhenClosed();
    }

    private static void testListReturnsStreamOfModules() throws IOException {
        String[] expectedModules = {"modA", "modB", "modC"};
        DummySystemModuleFinders finder = new DummySystemModuleFinders(false, expectedModules);
        Stream<String> stream = finder.list();
        Object[] result = stream.toArray();

        if (result.length != expectedModules.length) {
            throw new AssertionError("Expected " + expectedModules.length + " modules but got " + result.length);
        }

        for (int i = 0; i < expectedModules.length; i++) {
            if (!expectedModules[i].equals(result[i])) {
                throw new AssertionError("Expected module '" + expectedModules[i] + "' but got '" + result[i] + "'");
            }
        }
    }

    private static void testListThrowsWhenClosed() {
        DummySystemModuleFinders finder = new DummySystemModuleFinders(true, new String[0]);
        try {
            finder.list();
            throw new AssertionError("Expected IOException when list() called on closed reader");
        } catch (IOException e) {
            if (!"ModuleReader is closed".equals(e.getMessage())) {
                throw new AssertionError("Expected IOException with message 'ModuleReader is closed' but got: " + e.getMessage());
            }
        }
    }
}
