/*
 * @test
 * @summary Test java.util.stream.Nodes.SizedCollectorTask.begin(long) for exception and normal behavior
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    static class DummySizedCollectorTask extends Nodes.SizedCollectorTask<Object, Object[]> {

        DummySizedCollectorTask(Object[] array, int offset, int length) {
            super(null, null, array, offset, length);
        }

        @Override
        public Object[] getRawResult() {
            return null;
        }

        @Override
        protected void compute() {
            // no-op
        }
    }

    public static void main(String[] args) {
        // Setup array and parameters
        Object[] array = new Object[10];
        int offset = 0;
        int length = array.length;

        DummySizedCollectorTask task = new DummySizedCollectorTask(array, offset, length);

        // Test normal case: size <= length
        try {
            task.begin(10L); // size == length
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for size == length", e);
        }

        try {
            task.begin(5L); // size < length
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for size < length", e);
        }

        // Test exception case: size > length
        boolean threw = false;
        try {
            task.begin(11L); // size > length, should throw
        } catch (IllegalStateException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("size passed to Sink.begin exceeds array length")) {
                throw new RuntimeException("Exception message mismatch", e);
            }
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type", t);
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException not thrown");
        }
    }
}
