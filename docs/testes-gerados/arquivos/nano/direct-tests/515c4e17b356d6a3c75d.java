/* @test    @summary Test Selector.select(Consumer<SelectionKey>) method for correct behavior and exception handling    @run main SelectorWitupTest */

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.function.Consumer;

public class SelectorWitupTest {

    public static void main(String[] args) throws IOException {
        testSelectReturnsExpectedValue();
        testSelectThrowsIOException();
    }

    private static void testSelectReturnsExpectedValue() throws IOException {
        Selector selector = Selector.open();
        final boolean[] called = {false};
        Consumer<SelectionKey> action = key -> {
            called[0] = true;
            // No operation, just mark as called
        };
        int result = selector.select(action);
        // Since no channels are registered, expect 0
        assert result == 0 : "Expected select to return 0 when no channels are registered";
        assert called[0] : "Action should have been called";
        selector.close();
    }

    private static void testSelectThrowsIOException() {
        Selector selector = null;
        try {
            selector = new Selector() {
                @Override
                public int select(Consumer<SelectionKey> action) throws IOException {
                    throw new IOException("Simulated IOException");
                }
            };
            selector.select(key -> {});
            assert false : "Expected IOException to be thrown";
        } catch (IOException e) {
            assert e.getMessage().equals("Simulated IOException") : "Unexpected exception message";
        } finally {
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
