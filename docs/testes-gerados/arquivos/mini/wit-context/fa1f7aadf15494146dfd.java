/*
 * @test
 * @summary Test Signal.NativeHandler.handle throws UnsupportedOperationException
 * @run main SignalWitupTest
 */

public class SignalWitupTest {
    public static void main(String[] args) {
        final jdk.internal.misc.Signal.NativeHandler handler = new jdk.internal.misc.Signal.NativeHandler();
        final jdk.internal.misc.Signal sig = new jdk.internal.misc.Signal("INT");
        boolean thrown = false;
        try {
            handler.handle(sig);
        } catch (UnsupportedOperationException e) {
            thrown = true;
            if (!"invoking native signal handle not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
