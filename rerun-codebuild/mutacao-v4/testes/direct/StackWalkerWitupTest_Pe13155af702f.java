/* @test
   @summary Verifies the behavior of getCallerClass() method in StackWalker.
   @run main StackWalkerWitupTest_Pe13155af702f
 */
import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.util.Set;

public class StackWalkerWitupTest_Pe13155af702f {
    public static void main(String[] args) {
        testGetCallerClassWithRetainClassRef();
        testGetCallerClassWithoutRetainClassRef();
    }

    private static void testGetCallerClassWithRetainClassRef() {
        StackWalker walker = StackWalker.getInstance(Set.of(Option.RETAIN_CLASS_REFERENCE));
        Class<?> callerClass = null;
        try {
            callerClass = walker.getCallerClass();
        } catch (UnsupportedOperationException e) {
            throw new AssertionError("Expected getCallerClass() to succeed with RETAIN_CLASS_REFERENCE option", e);
        }
        if (callerClass == null) {
            throw new AssertionError("getCallerClass() returned null");
        }
        // The caller class should be this class or a class in the call stack
        if (!callerClass.equals(StackWalkerWitupTest_Pe13155af702f.class)) {
            throw new AssertionError("Unexpected caller class: " + callerClass);
        }
    }

    private static void testGetCallerClassWithoutRetainClassRef() {
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.getCallerClass();
            throw new AssertionError("Expected UnsupportedOperationException when RETAIN_CLASS_REFERENCE is not set");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
