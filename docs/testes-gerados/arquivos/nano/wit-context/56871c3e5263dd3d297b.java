/* @test    @summary Test for StackWalker.getMethodType() method to verify exception behavior.    @run main StackWalkerGetMethodTypeTest */

import java.lang.StackWalker;
import java.lang.StackWalker.StackFrame;
import java.lang.invoke.MethodType;

public class StackWalkerGetMethodTypeTest {

    public static void main(String[] args) {
        testGetMethodTypeThrowsException();
    }

    static void testGetMethodTypeThrowsException() {
        StackWalker walker = StackWalker.getInstance();
        StackFrame frame = new StackFrame() {
            @Override
            public MethodType getMethodType() {
                throw new UnsupportedOperationException();
            }
            @Override
            public String getDeclaringClassName() {
                return "TestClass";
            }
            @Override
            public String getMethodName() {
                return "testMethod";
            }
            @Override
            public int getByteCodeIndex() {
                return 0;
            }
            @Override
            public String getClassName() {
                return "TestClass";
            }
        };
        try {
            frame.getMethodType();
            // If no exception, the test fails
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
