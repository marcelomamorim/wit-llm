/*
 * @test
 * @summary Verifies that StackFrame.getDescriptor() returns a method descriptor for frames obtained from StackWalker.walk
 * @run main StackWalkerWitupTest
 */

import java.util.Optional;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        StackWalker walker = StackWalker.getInstance();
        Optional<StackWalker.StackFrame> frame = walker.walk(s ->
                s.filter(f -> f.getMethodName().equals("main")).findFirst());
        if (!frame.isPresent()) {
            throw new RuntimeException("main frame not found");
        }
        String descriptor = frame.get().getDescriptor();
        if (descriptor == null) {
            throw new RuntimeException("descriptor is null");
        }
        if (!descriptor.startsWith("([Ljava/lang/String;)")) {
            throw new RuntimeException("unexpected descriptor: " + descriptor);
        }
    }
}
