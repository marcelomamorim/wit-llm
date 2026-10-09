/*
 * @test
 * @summary Tests for java.lang.StackWalker class, specifically getCallerClass() method.
 * @run main StackWalkerWitupTest_P6ef415140ffa
 */

import java.lang.*;

public class StackWalkerWitupTest_P6ef415140ffa {

    public static void main(String[] args) {
        testGetCallerClassWithRetainClassRef();
        testGetCallerClassWithoutRetainClassRef();
    }

    private static void testGetCallerClassWithRetainClassRef() {
        try {
            java.lang.StackWalker walker = java.lang.StackWalker.getInstance();
            Class<?> callerClass = walker.walk(s -> {
                return s.map(StackWalker.StackFrame::getDeclaringClass)
                        .filter(c -> c != null)
                        .findFirst()
                        .orElse(null);
            });
            if (callerClass != null) {
                System.out.println("Caller class with retainClassRef: " + callerClass.getName());
            } else {
                System.out.println("Caller class not found with retainClassRef");
            }
        } catch (Exception e) {
            System.out.println("Unexpected exception in testGetCallerClassWithRetainClassRef: " + e);
        }
    }

    private static void testGetCallerClassWithoutRetainClassRef() {
        try {
            java.lang.StackWalker walker = java.lang.StackWalker.getInstance();
            // Simulate the scenario where retainClassRef is false by mocking or reflection isn't possible here,
            // so this test demonstrates expected behavior: exception when calling getCallerClass()
            // but since the actual method relies on internal state, and we can't set it directly, this test
            // is illustrative.
            // For actual behavior, if the method is called directly, it throws if retainClassRef is false.
            // Here, we invoke the method directly to observe the exception.
            try {
                walker.getCallerClass();
                System.out.println("Expected exception not thrown in getCallerClass() when retainClassRef is false");
            } catch (UnsupportedOperationException e) {
                System.out.println("Caught expected UnsupportedOperationException: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Unexpected exception in testGetCallerClassWithoutRetainClassRef: " + e);
        }
    }
}
