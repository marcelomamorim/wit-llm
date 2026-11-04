/* @test    @summary Test SelectorImpl.select() method for basic behavior and exception handling    @run main SelectorImplWitupTest */

import java.io.IOException;
import sun.nio.ch.SelectorImpl;

public class SelectorImplWitupTest {

    public static void main(String[] args) {
        testSelectReturnsInt();
        testSelectThrowsWhenClosed();
    }

    static void testSelectReturnsInt() {
        SelectorImpl selector = null;
        try {
            selector = new SelectorImpl(java.nio.channels.spi.SelectorProvider.provider());
            int result = selector.select();
            if (result < 0) {
                throw new AssertionError("select() returned negative value: " + result);
            }
            System.out.println("testSelectReturnsInt passed, result: " + result);
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException during select", e);
        } catch (IllegalStateException e) {
            throw new AssertionError("Selector is in invalid state", e);
        } finally {
            if (selector != null && selector.isOpen()) {
                try {
                    selector.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    static void testSelectThrowsWhenClosed() {
        SelectorImpl selector = null;
        try {
            selector = new SelectorImpl(java.nio.channels.spi.SelectorProvider.provider());
            selector.close();
            try {
                selector.select();
                throw new AssertionError("Expected ClosedSelectorException or IllegalSelectorException");
            } catch (java.nio.channels.ClosedSelectorException | java.nio.channels.IllegalSelectorException e) {
                System.out.println("testSelectThrowsWhenClosed passed, caught expected exception: " + e.getClass().getSimpleName());
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException during test setup", e);
        } finally {
            if (selector != null && selector.isOpen()) {
                try {
                    selector.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
