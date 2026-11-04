/*
 * @test
 * @summary Test that Signal.handle(Signal) throws UnsupportedOperationException
 * @run main SignalWitupTest
 */

public class SignalWitupTest {
    public static void main(String[] args) {
        jdk.internal.misc.Signal signal = new jdk.internal.misc.Signal("INT");
        try {
            signal.handle(signal);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
