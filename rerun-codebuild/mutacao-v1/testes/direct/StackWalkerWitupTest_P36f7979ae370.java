/* @test    @summary Test getCallerClass() method of StackWalker for supported scenarios    @run main StackWalkerWitupTest_P36f7979ae370 */

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;

public class StackWalkerWitupTest_P36f7979ae370 {

    public static void main(String[] args) {
        testGetCallerClassWithClassRef();
        testGetCallerClassWithoutClassRef();
    }

    private static void testGetCallerClassWithClassRef() {
        StackWalker walker = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
        Class<?> callerClass = null;
        try {
            callerClass = walker.getCallerClass();
        } catch (UnsupportedOperationException e) {
            throw new RuntimeException("Unexpected UnsupportedOperationException when retain class reference is enabled", e);
        }
        if (callerClass == null) {
            throw new AssertionError("Expected non-null caller class when retain class reference is enabled");
        }
        // The caller class should be this class or a class in the call stack
        if (!StackWalkerWitupTest_P36f7979ae370.class.equals(callerClass)) {
            // It might be the test method or main, so check if it's in the call stack
            boolean found = false;
            for (StackFrame frame : StackWalker.getInstance().walk(s -> s)) {
                if (frame.getDeclaringClass().equals(callerClass)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new AssertionError("Caller class not found in current call stack");
            }
        }
    }

    private static void testGetCallerClassWithoutClassRef() {
        // Create a StackWalker without RETAIN_CLASS_REFERENCE option
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.getCallerClass();
            throw new AssertionError("Expected UnsupportedOperationException when retain class reference is not enabled");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
