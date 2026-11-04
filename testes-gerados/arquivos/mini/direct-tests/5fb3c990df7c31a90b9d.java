/*
 * @test
 * @summary Test StackWalker.walk method with simple and edge cases
 * @run main StackWalkerWitupTest
 */

import java.lang.StackWalker;
import java.lang.StackWalker.StackFrame;
import java.util.stream.Stream;
import java.util.function.Function;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        testWalkReturnsExpectedResult();
        testWalkWithNullFunctionThrowsNPE();
        testWalkStreamContainsCurrentMethod();
    }

    private static void testWalkReturnsExpectedResult() {
        StackWalker walker = StackWalker.getInstance();
        String result = walker.walk(new Function<Stream<StackFrame>, String>() {
            @Override
            public String apply(Stream<StackFrame> stream) {
                // Collect the first method name from the stack
                return stream.findFirst().map(StackFrame::getMethodName).orElse("none");
            }
        });
        if (result == null || result.isEmpty()) {
            throw new AssertionError("walk returned null or empty method name");
        }
    }

    private static void testWalkWithNullFunctionThrowsNPE() {
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.walk(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testWalkStreamContainsCurrentMethod() {
        StackWalker walker = StackWalker.getInstance();
        boolean found = walker.walk(new Function<Stream<StackFrame>, Boolean>() {
            @Override
            public Boolean apply(Stream<StackFrame> stream) {
                return stream.anyMatch(frame -> "testWalkStreamContainsCurrentMethod".equals(frame.getMethodName()));
            }
        });
        if (!found) {
            throw new AssertionError("Current method not found in stack frames");
        }
    }
}
