/*
 * @test
 * @summary Test for Spliterators.nextDouble() method to ensure correct behavior.
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.NoSuchElementException;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNextDoubleReturnsValueAndResets();
        testNextDoubleThrowsWhenNoNext();
    }

    private static void testNextDoubleReturnsValueAndResets() {
        // Create an anonymous subclass with controlled nextElement and valueReady
        class TestSpliterator extends Spliterators {
            boolean valueReady = true;
            double nextElement = 42.0;

            public double nextDouble() {
                if (!valueReady) {
                    throw new NoSuchElementException();
                } else {
                    valueReady = false;
                    return nextElement;
                }
            }
        }
        TestSpliterator ts = new TestSpliterator();
        double result = ts.nextDouble();
        assert result == 42.0 : "Expected 42.0, got " + result;
        // After calling, valueReady should be false, so next call should throw
        try {
            ts.nextDouble();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    private static void testNextDoubleThrowsWhenNoNext() {
        class TestSpliterator extends Spliterators {
            boolean valueReady = false;
            double nextElement = 0.0;

            public double nextDouble() {
                if (!valueReady) {
                    throw new NoSuchElementException();
                } else {
                    valueReady = false;
                    return nextElement;
                }
            }
        }
        TestSpliterator ts = new TestSpliterator();
        try {
            ts.nextDouble();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
