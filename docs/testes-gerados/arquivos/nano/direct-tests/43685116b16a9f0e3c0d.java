/* @test    @summary Test SelectorImpl.selectNow method for normal operation and null argument handling    @run main SelectorImplWitupTest */

import sun.nio.ch.SelectorImpl;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.function.Consumer;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws IOException {
        testSelectNowWithValidConsumer();
        testSelectNowWithNullConsumer();
    }

    private static void testSelectNowWithValidConsumer() throws IOException {
        SelectorImpl selector = new SelectorImpl(null);
        Consumer<SelectionKey> consumer = key -> {};
        int result = selector.selectNow(consumer);
        if (result < 0) {
            throw new AssertionError("Expected non-negative result, got: " + result);
        }
        System.out.println("testSelectNowWithValidConsumer passed.");
    }

    private static void testSelectNowWithNullConsumer() {
        try {
            SelectorImpl selector = new SelectorImpl(null);
            selector.selectNow(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException e) {
            System.out.println("testSelectNowWithNullConsumer passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
