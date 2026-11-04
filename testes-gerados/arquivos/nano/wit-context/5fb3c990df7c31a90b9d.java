/*
 * @test
 * @summary Tests the StackWalker.walk method for null argument handling.
 * @run main StackWalkerWitupTest
 */

import java.lang.StackWalker;
import java.lang.StackWalker.StackFrame;
import java.util.stream.Stream;
import java.util.function.Function;

public class StackWalkerWitupTest {

    public static void main(String[] args) {
        testWalkWithNullFunction();
        testWalkWithValidFunction();
    }

    private static void testWalkWithNullFunction() {
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.walk(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testWalkWithValidFunction() {
        StackWalker walker = StackWalker.getInstance();
        try {
            String result = walker.walk(s -> {
                // Collect the class names of the top few stack frames
                return s.limit(3).map(StackFrame::getDeclaringClass).toString();
            });
            if (result != null && result.contains("java.lang")) {
                System.out.println("PASS: walk with valid function returned: " + result);
            } else {
                System.out.println("FAIL: walk with valid function returned unexpected result: " + result);
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Exception during walk with valid function: " + t);
        }
    }
}
