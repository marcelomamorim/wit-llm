/*
 * @test
 * @summary Witup-generated checks for StackWalker.walk null handling and basic traversal
 * @run main StackWalkerWitupTest
 */

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.walk(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        long count = walker.walk(s -> s.count());
        if (count <= 0) {
            throw new AssertionError("expected at least one frame, got " + count);
        }
        String top = walker.walk(s -> s.findFirst().get().getMethodName());
        if (!"main".equals(top)) {
            throw new AssertionError("unexpected top frame: " + top);
        }
    }
}
