/*
 * @test
 * @summary Verifies AbstractPipeline.onClose: returns this, rejects null and linked streams, and runs close handlers in registration order
 * @run main AbstractPipelineWitupTest
 */
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AbstractPipelineWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsSameStream() {
        Stream<Integer> s = Stream.of(1, 2, 3);
        Stream<Integer> r = s.onClose(() -> { });
        check(r == s, "onClose must return the same stream instance");
        r.close();
    }

    static void testNullHandlerRejected() {
        Stream<Integer> s = Stream.of(1, 2, 3);
        try {
            s.onClose(null);
            throw new RuntimeException("expected NullPointerException for null handler");
        } catch (NullPointerException expected) {
        }
    }

    static void testLinkedStreamRejected() {
        Stream<Integer> s = Stream.of(1, 2, 3);
        Stream<Integer> mapped = s.map(x -> x + 1);
        try {
            s.onClose(() -> { });
            throw new RuntimeException("expected IllegalStateException for linked stream");
        } catch (IllegalStateException expected) {
        }
        mapped.close();
    }

    static void testHandlerRunsOnClose() {
        int[] count = {0};
        Stream<Integer> s = Stream.of(1, 2, 3).onClose(() -> count[0]++);
        check(count[0] == 0, "handler must not run before close");
        s.close();
        check(count[0] == 1, "handler must run exactly once on close");
    }

    static void testHandlersRunInOrder() {
        List<String> log = new ArrayList<>();
        Stream<Integer> s = Stream.of(1, 2, 3)
                .onClose(() -> log.add("first"))
                .onClose(() -> log.add("second"))
                .onClose(() -> log.add("third"));
        s.close();
        check(log.size() == 3, "all three handlers must run, got " + log);
        check(log.get(0).equals("first"), "first handler must run first, got " + log);
        check(log.get(1).equals("second"), "second handler must run second, got " + log);
        check(log.get(2).equals("third"), "third handler must run third, got " + log);
    }

    static void testHandlerRegisteredOnDerivedStageRunsOnClose() {
        List<String> log = new ArrayList<>();
        Stream<Integer> s = Stream.of(1, 2, 3);
        Stream<Integer> mapped = s.map(x -> x * 2);
        mapped = mapped.onClose(() -> log.add("closed"));
        mapped.close();
        check(log.size() == 1 && log.get(0).equals("closed"),
              "handler registered on derived stage must run on close, got " + log);
    }

    static void testFailingHandlerDoesNotPreventLaterHandlers() {
        List<String> log = new ArrayList<>();
        Stream<Integer> s = Stream.of(1, 2, 3)
                .onClose(() -> { throw new IllegalArgumentException("boom"); })
                .onClose(() -> log.add("second"));
        try {
            s.close();
            throw new RuntimeException("expected exception from failing handler");
        } catch (IllegalArgumentException expected) {
            check("boom".equals(expected.getMessage()), "unexpected message: " + expected.getMessage());
        }
        check(log.size() == 1 && log.get(0).equals("second"),
              "later handler must still run after a failing handler, got " + log);
    }

    public static void main(String[] args) {
        testReturnsSameStream();
        testNullHandlerRejected();
        testLinkedStreamRejected();
        testHandlerRunsOnClose();
        testHandlersRunInOrder();
        testHandlerRegisteredOnDerivedStageRunsOnClose();
        testFailingHandlerDoesNotPreventLaterHandlers();
    }
}
