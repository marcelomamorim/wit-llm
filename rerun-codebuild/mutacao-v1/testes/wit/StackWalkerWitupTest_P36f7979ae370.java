/* @test    @summary Test for StackWalker.getCallerClass() method behavior.    @run main StackWalkerWitupTest_P36f7979ae370 */

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;

public class StackWalkerWitupTest_P36f7979ae370 {

    public static void main(String[] args) {
        testGetCallerClassWithRetainClassRef();
        testGetCallerClassWithoutRetainClassRef();
    }

    private static void testGetCallerClassWithRetainClassRef() {
        StackWalker walker = StackWalker.getInstance(EnumSet.of(Option.RETAIN_CLASS_REFERENCE));
        Class<?> callerClass = null;
        try {
            callerClass = walker.getCallerClass();
        } catch (Throwable t) {
            throw new AssertionError("Expected getCallerClass() to succeed with RETAIN_CLASS_REFERENCE option", t);
        }
        if (callerClass == null) {
            throw new AssertionError("getCallerClass() returned null when retainClassRef is true");
        }
        System.out.println("Caller class with retainClassRef: " + callerClass.getName());
    }

    private static void testGetCallerClassWithoutRetainClassRef() {
        StackWalker walker = StackWalker.getInstance();
        try {
            Class<?> cls = walker.getCallerClass();
            throw new AssertionError("Expected getCallerClass() to throw UnsupportedOperationException when retainClassRef is false, but got: " + cls);
        } catch (UnsupportedOperationException e) {
            // Expected exception
            System.out.println("Correctly threw UnsupportedOperationException when retainClassRef is false");
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type when retainClassRef is false", t);
        }
    }
}
