/*
 * @test
 * @summary Witup-generated checks for StackFrame.getDeclaringClass via public StackWalker API
 * @run main StackFrameInfoWitupTest
 */
import java.lang.StackWalker.StackFrame;
import java.util.Optional;

public class StackFrameInfoWitupTest {

    public static void main(String[] args) {
        testRetainClassRefReturnsDeclaringClass();
        testWithoutRetainClassRefThrowsUnsupportedOperation();
    }

    static void testRetainClassRefReturnsDeclaringClass() {
        StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REF);
        Optional<Class<?>> found = walker.walk(s -> s
                .filter(f -> f.getMethodName().equals("testRetainClassRefReturnsDeclaringClass"))
                .map(StackFrame::getDeclaringClass)
                .findFirst());
        if (!found.isPresent()) {
            throw new RuntimeException("frame for test method not found");
        }
        if (found.get() != StackFrameInfoWitupTest.class) {
            throw new RuntimeException("unexpected declaring class: " + found.get());
        }
    }

    static void testWithoutRetainClassRefThrowsUnsupportedOperation() {
        StackWalker walker = StackWalker.getInstance();
        boolean threw = walker.walk(s -> {
            StackFrame f = s.findFirst().get();
            try {
                f.getDeclaringClass();
                return false;
            } catch (UnsupportedOperationException e) {
                return true;
            }
        });
        if (!threw) {
            throw new RuntimeException("getDeclaringClass did not throw UnsupportedOperationException without RETAIN_CLASS_REF");
        }
    }
}
