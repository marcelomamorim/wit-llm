/* @test    @summary Test for StackWalker.getCallerClass() method behavior.    @run main StackWalkerWitupTest */

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;
import java.util.Optional;

public class StackWalkerWitupTest {

    public static void main(String[] args) {
        testGetCallerClassWhenRetainClassRef();
        testGetCallerClassWhenNotRetainClassRef();
    }

    private static void testGetCallerClassWhenRetainClassRef() {
        StackWalker walker = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
        try {
            Class<?> callerClass = walker.getCallerClass();
            if (callerClass == null) {
                System.err.println("Failed: getCallerClass() returned null when retainClassRef is true.");
            } else {
                System.out.println("Passed: getCallerClass() returned class: " + callerClass.getName());
            }
        } catch (Throwable t) {
            System.err.println("Failed: Exception thrown when retainClassRef is true: " + t);
        }
    }

    private static void testGetCallerClassWhenNotRetainClassRef() {
        StackWalker walker = StackWalker.getInstance();
        try {
            Class<?> callerClass = walker.getCallerClass();
            System.err.println("Failed: Expected exception when retainClassRef is false, but got: " + callerClass);
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed: Correct exception thrown when retainClassRef is false.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }
}
