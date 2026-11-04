/*
 * @test
 * @summary Behavioral tests for StackWalker.walk: result propagation, null argument, caller frame visibility and stream closure
 * @run main StackWalkerWitupTest
 */
import java.util.stream.Stream;

public class StackWalkerWitupTest {

    public static void main(String[] args) throws Exception {
        testReturnsFunctionResult();
        testNullFunctionThrowsNPE();
        testStackContainsCaller();
        testStreamClosedAfterWalk();
    }

    static void testReturnsFunctionResult() {
        StackWalker walker = StackWalker.getInstance();
        Integer r = walker.walk(s -> 42);
        if (r == null || r.intValue() != 42) {
            throw new RuntimeException("expected 42 but got " + r);
        }
    }

    static void testNullFunctionThrowsNPE() {
        StackWalker walker = StackWalker.getInstance();
        boolean thrown = false;
        try {
            walker.walk(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null function");
        }
    }

    static void testStackContainsCaller() {
        StackWalker walker = StackWalker.getInstance();
        Boolean found = walker.walk(s ->
                s.map(StackWalker.StackFrame::getMethodName)
                 .anyMatch("testStackContainsCaller"::equals));
        if (found == null || !found.booleanValue()) {
            throw new RuntimeException("caller frame not found in walked stack");
        }
    }

    static void testStreamClosedAfterWalk() {
        StackWalker walker = StackWalker.getInstance();
        Object[] holder = new Object[1];
        walker.walk(st -> {
            holder[0] = st;
            return null;
        });
        Stream<?> captured = (Stream<?>) holder[0];
        boolean thrown = false;
        try {
            captured.count();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalStateException when reusing closed stream");
        }
    }
}
