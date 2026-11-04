/*
 * @test
 * @summary Basic tests for StackWalker.walk method
 * @run main StackWalkerWitupTest
 */
import java.lang.StackWalker;
import java.lang.StackWalker.StackFrame;
import java.util.stream.Stream;
import java.util.function.Function;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        testWalkReturnsStreamOfStackFrames();
        testWalkWithNullFunctionThrows();
        testWalkReturnsExpectedResult();
    }

    static void testWalkReturnsStreamOfStackFrames() {
        StackWalker walker = StackWalker.getInstance();
        String result = walker.walk(s -> {
            long count = s.count();
            if (count > 0) {
                return "ok";
            } else {
                return "fail";
            }
        });
        if (!"ok".equals(result)) {
            throw new AssertionError("Expected 'ok', got: " + result);
        }
    }

    static void testWalkWithNullFunctionThrows() {
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.walk(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testWalkReturnsExpectedResult() {
        StackWalker walker = StackWalker.getInstance();
        String message = "hello";
        String result = walker.walk(s -> {
            return message + " world";
        });
        if (!"hello world".equals(result)) {
            throw new AssertionError("Expected 'hello world', got: " + result);
        }
    }
}
