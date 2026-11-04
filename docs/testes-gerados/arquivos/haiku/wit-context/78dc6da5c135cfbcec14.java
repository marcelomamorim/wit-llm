/*
 * @test
 * @summary Verify StackWalker.forEach rejects a null action and visits the current stack frames
 * @run main StackWalkerWitupTest
 */
import java.util.ArrayList;
import java.util.List;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testForEachVisitsMainFrame();
        testRetainClassReferenceDeclaringClass();
    }

    static void testNullActionThrowsNPE() {
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.forEach(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testForEachVisitsMainFrame() {
        StackWalker walker = StackWalker.getInstance();
        final List<String> names = new ArrayList<>();
        walker.forEach(f -> names.add(f.getMethodName()));
        if (names.isEmpty()) {
            throw new AssertionError("expected at least one stack frame");
        }
        if (!names.contains("main")) {
            throw new AssertionError("expected frame named main, got " + names);
        }
    }

    static void testRetainClassReferenceDeclaringClass() {
        StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        final List<Class<?>> classes = new ArrayList<>();
        walker.forEach(f -> classes.add(f.getDeclaringClass()));
        if (!classes.contains(StackWalkerWitupTest.class)) {
            throw new AssertionError("expected declaring class StackWalkerWitupTest in stack");
        }
    }
}
