/* @test    @summary Test getCallerClass method in StackWalker
 * @run main StackWalkerWitupTest_P6ef415140ffa
 */

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;

public class StackWalkerWitupTest_P6ef415140ffa {

    public static void main(String[] args) {
        testGetCallerClassWithRetainClassRef();
        testGetCallerClassWithoutRetainClassRef();
    }

    private static void testGetCallerClassWithRetainClassRef() {
        StackWalker walker = StackWalker.getInstance(EnumSet.of(Option.RETAIN_CLASS_REFERENCE));
        Class<?> callerClass = null;
        try {
            callerClass = walker.getCallerClass();
        } catch (UnsupportedOperationException e) {
            // Not expected in this test as retain class ref is enabled
        }
        // Verify that the returned class is not null and is a class
        if (callerClass == null) {
            throw new AssertionError("Expected non-null caller class when retain class reference is enabled.");
        }
        if (!callerClass.isInterface() && !callerClass.isPrimitive()) {
            // Basic check passed
        } else {
            throw new AssertionError("Caller class should be a regular class.");
        }
        // Optionally, print the class name for verification
        System.out.println("Caller class with retain reference: " + callerClass.getName());
    }

    private static void testGetCallerClassWithoutRetainClassRef() {
        try {
            StackWalker walker = StackWalker.getInstance(EnumSet.noneOf(Option.class));
            Class<?> callerClass = walker.getCallerClass();
            throw new AssertionError("Expected UnsupportedOperationException when retain class reference is not enabled.");
        } catch (UnsupportedOperationException e) {
            // Expected exception
            String message = e.getMessage();
            if (message == null || !message.contains("does not have RETAIN_CLASS_REFERENCE access")) {
                throw new AssertionError("Unexpected exception message: " + message, e);
            }
        }
    }
}
