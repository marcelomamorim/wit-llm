/*
 * @test
 * @summary Test StackWalker.walk(Function) for null argument and basic usage
 * @run main StackWalkerWitupTest
 */

import java.lang.StackWalker;
import java.lang.StackWalker.StackFrame;
import java.util.stream.Stream;
import java.util.function.Function;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        testWalkNullFunction();
        testWalkBasicUsage();
    }

    private static void testWalkNullFunction() {
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.walk(null);
            throw new AssertionError("Expected NullPointerException for null function argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testWalkBasicUsage() {
        StackWalker walker = StackWalker.getInstance();
        // Use walk to get the name of the top stack frame method
        String topMethodName = walker.walk(new Function<Stream<StackFrame>, String>() {
            @Override
            public String apply(Stream<StackFrame> stream) {
                StackFrame frame = stream.findFirst().orElse(null);
                if (frame == null) {
                    return null;
                }
                return frame.getMethodName();
            }
        });
        if (topMethodName == null || topMethodName.isEmpty()) {
            throw new AssertionError("walk did not return a valid top method name");
        }
    }
}
