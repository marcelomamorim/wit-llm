/*
 * @test
 * @summary Test StackWalker.getCallerClass() behavior
 * @run main StackWalkerWitupTest
 */

import java.lang.StackWalker;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        testGetCallerClassWithoutRetainClassReference();
        testGetCallerClassWithRetainClassReference();
    }

    private static void testGetCallerClassWithoutRetainClassReference() {
        // Create a StackWalker instance without RETAIN_CLASS_REFERENCE option
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.getCallerClass();
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("RETAIN_CLASS_REFERENCE")) {
                throw new Error("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testGetCallerClassWithRetainClassReference() {
        // Create a StackWalker instance with RETAIN_CLASS_REFERENCE option
        StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        Class<?> callerClass = walker.getCallerClass();
        if (callerClass == null) {
            throw new Error("getCallerClass() returned null");
        }
        // The caller class should be this test class
        if (!callerClass.equals(StackWalkerWitupTest.class)) {
            throw new Error("getCallerClass() returned unexpected class: " + callerClass.getName());
        }
    }
}
