/*
 * @test
 * @summary Test for sun.nio.ch.SelectorImpl.select()
 * @run main SelectorImplWitupTest
 */

import java.io.IOException;
import sun.nio.ch.SelectorImpl;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSelectReturnsInt();
        testSelectThrowsIllegalArgumentExceptionOnNegativeTimeout();
        testSelectThrowsNullPointerException();
    }

    public static void testSelectReturnsInt() throws IOException {
        SelectorImpl selector = new SelectorImpl(null);
        int result = selector.select();
        if (result < 0) {
            throw new AssertionError("Expected non-negative result, got: " + result);
        }
        System.out.println("testSelectReturnsInt passed.");
    }

    public static void testSelectThrowsIllegalArgumentExceptionOnNegativeTimeout() throws IOException {
        SelectorImpl selector = new SelectorImpl(null);
        try {
            // Call lockAndDoSelect directly with negative timeout and non-null action to simulate the condition
            selector.lockAndDoSelect(
                new java.util.function.Consumer<java.nio.channels.SelectionKey>() {
                    public void accept(java.nio.channels.SelectionKey key) {}
                },
                -1
            );
            throw new AssertionError("Expected IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("testSelectThrowsIllegalArgumentExceptionOnNegativeTimeout passed.");
        }
    }

    public static void testSelectThrowsNullPointerException() throws IOException {
        SelectorImpl selector = new SelectorImpl(null);
        try {
            // Call lockAndDoSelect with null action to trigger NullPointerException
            selector.lockAndDoSelect(null, -1);
            throw new AssertionError("Expected NullPointerException not thrown.");
        } catch (NullPointerException e) {
            System.out.println("testSelectThrowsNullPointerException passed.");
        }
    }
}
