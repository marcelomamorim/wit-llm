/*
 * @test
 * @summary Test StackWalker.StackFrame.getDescriptor() throws UnsupportedOperationException
 * @run main StackWalkerWitupTest
 */

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        // Create an anonymous StackFrame implementation to test getDescriptor default method
        java.lang.StackWalker.StackFrame frame = new java.lang.StackWalker.StackFrame() {
            @Override
            public String getClassName() {
                return "TestClass";
            }

            @Override
            public String getMethodName() {
                return "testMethod";
            }

            @Override
            public int getLineNumber() {
                return 42;
            }

            @Override
            public String getFileName() {
                return "TestFile.java";
            }

            @Override
            public Class<?> getDeclaringClass() {
                return StackWalkerWitupTest.class;
            }
        };

        // Test that getDescriptor throws UnsupportedOperationException
        try {
            frame.getDescriptor();
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception, test passes
        }
    }
}
