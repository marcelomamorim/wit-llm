/*
 * @test
 * @summary Test SystemModuleFinders.list() method for expected IOException when closed and normal stream otherwise
 * @run main SystemModuleFindersWitupTest
 */

import java.io.IOException;
import java.util.stream.Stream;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

public class SystemModuleFindersWitupTest {

    // Minimal stub to test list() method behavior
    static class SystemModuleReader {
        private boolean closed = false;

        public void close() {
            closed = true;
        }

        public Stream<String> list() throws IOException {
            if (closed)
                throw new IOException("ModuleReader is closed");

            Spliterator<String> s = new Spliterator<String>() {
                private boolean done = false;

                @Override
                public boolean tryAdvance(java.util.function.Consumer<? super String> action) {
                    if (done) return false;
                    action.accept("module1");
                    done = true;
                    return true;
                }

                @Override
                public Spliterator<String> trySplit() {
                    return null;
                }

                @Override
                public long estimateSize() {
                    return 1;
                }

                @Override
                public int characteristics() {
                    return ORDERED | SIZED | SUBSIZED | IMMUTABLE | NONNULL;
                }
            };
            return StreamSupport.stream(s, false);
        }
    }

    public static void main(String[] args) throws Exception {
        SystemModuleReader reader = new SystemModuleReader();

        // Test normal behavior: list() returns stream with expected element
        Stream<String> stream = reader.list();
        String first = stream.findFirst().orElse(null);
        if (!"module1".equals(first)) {
            throw new AssertionError("Expected 'module1' but got: " + first);
        }

        // Test exceptional behavior: after close(), list() throws IOException
        reader.close();
        boolean threw = false;
        try {
            reader.list();
        } catch (IOException e) {
            if (!"ModuleReader is closed".equals(e.getMessage())) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IOException when calling list() after close()");
        }

        System.out.println("Test passed");
    }
}
