/*
 * @test
 * @summary Test StackWalker.getCallerClass() behavior with and without RETAIN_CLASS_REFERENCE option
 * @run main StackWalkerWitupTest_P24666b3b2077
 */

import java.lang.StackWalker;
import java.lang.UnsupportedOperationException;
import java.util.EnumSet;

public class StackWalkerWitupTest_P24666b3b2077 {
    public static void main(String[] args) {
        testGetCallerClassWithoutRetainClassRef();
        testGetCallerClassWithRetainClassRef();
    }

    private static void testGetCallerClassWithoutRetainClassRef() {
        // Create a StackWalker instance without RETAIN_CLASS_REFERENCE option
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.getCallerClass();
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("RETAIN_CLASS_REFERENCE")) {
                throw new AssertionError("Exception message does not mention RETAIN_CLASS_REFERENCE");
            }
        }
    }

    private static void testGetCallerClassWithRetainClassRef() {
        // Create a StackWalker instance with RETAIN_CLASS_REFERENCE option
        StackWalker walker = StackWalker.getInstance(EnumSet.of(StackWalker.Option.RETAIN_CLASS_REFERENCE));
        Class<?> callerClass = walker.getCallerClass();
        if (callerClass == null) {
            throw new AssertionError("getCallerClass() returned null");
        }
        // The caller class should be this test class
        if (!callerClass.equals(StackWalkerWitupTest_P24666b3b2077.class)) {
            throw new AssertionError("getCallerClass() returned unexpected class: " + callerClass.getName());
        }
    }
}
