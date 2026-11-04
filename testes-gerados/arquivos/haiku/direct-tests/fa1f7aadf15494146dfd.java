/*
 * @test
 * @summary Verify that Signal.handle(Signal) throws UnsupportedOperationException and that the Signal name is preserved
 * @modules java.base/jdk.internal.misc
 * @run main SignalWitupTest
 */

import jdk.internal.misc.Signal;

public class SignalWitupTest {
    public static void main(String[] args) {
        Signal sig = new Signal("INT");

        boolean thrown = false;
        try {
            sig.handle(new Signal("INT"));
        } catch (UnsupportedOperationException e) {
            thrown = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not supported")) {
                throw new RuntimeException("Unexpected message: " + msg);
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from Signal.handle");
        }

        if (!"INT".equals(sig.getName())) {
            throw new RuntimeException("Unexpected signal name: " + sig.getName());
        }
    }
}
