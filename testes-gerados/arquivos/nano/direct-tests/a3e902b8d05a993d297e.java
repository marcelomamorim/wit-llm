/*
 * @test
 * @summary Test SelectorImpl.select() method for correct behavior.
 * @run main SelectorImplWitupTest
 */

import java.io.IOException;
import java.nio.channels.SelectionKey;
import sun.nio.ch.SelectorImpl;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws IOException {
        testSelectMethodReturnsExpected();
    }

    public static void testSelectMethodReturnsExpected() throws IOException {
        SelectorImpl selector = new SelectorImpl(null);
        int result = selector.select((SelectionKey key) -> {
            // No operation, just a placeholder
        });
        // Since no channels are registered, expect 0
        assert result == 0 : "Expected select to return 0 when no channels are registered, but got " + result;
        // Also verify that keys set is empty
        assert selector.keys().isEmpty() : "Expected keys set to be empty";
        // And selectedKeys set is empty
        assert selector.selectedKeys().isEmpty() : "Expected selectedKeys set to be empty";
    }
}
