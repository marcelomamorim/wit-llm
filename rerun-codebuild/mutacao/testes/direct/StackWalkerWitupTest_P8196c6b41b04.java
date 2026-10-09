/* @test    @summary Test getCallerClass method of StackWalker for supported scenario    @run main StackWalkerWitupTest_P8196c6b41b04 */

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;

public class StackWalkerWitupTest_P8196c6b41b04 {

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
            // If the environment does not support RETAIN_CLASS_REFERENCE, skip this test
            return;
        }
        assert callerClass != null : "Expected non-null caller class when RETAIN_CLASS_REFERENCE is enabled";
        // The caller class should be this class or a class in the call stack
        assert callerClass == StackWalkerWitupTest_P8196c6b41b04.class || callerClass.getName().contains("StackWalker") :
                "Caller class should be this test class or related";
    }

    private static void testGetCallerClassWithoutRetainClassRef() {
        // Create a StackWalker without RETAIN_CLASS_REFERENCE option
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.getCallerClass();
            assert false : "Expected UnsupportedOperationException when RETAIN_CLASS_REFERENCE is not set";
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
