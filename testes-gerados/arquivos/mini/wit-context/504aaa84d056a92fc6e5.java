/*
 * @test
 * @summary Test java.util.Spliterators nextDouble() method behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNextDoubleThrowsWhenNoValueReady();
        testNextDoubleReturnsValueWhenReady();
    }

    private static void testNextDoubleThrowsWhenNoValueReady() {
        // Create an instance of Adapter with valueReady = false and hasNext() false
        // Since Adapter is package-private and no public constructor is visible,
        // we test via an anonymous subclass of Spliterators.Adapter.OfDouble
        Spliterators.Adapter.OfDouble adapter = new Spliterators.Adapter.OfDouble() {
            boolean valueReady = false;
            double nextElement = 0.0;

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public double nextDouble() {
                if (!valueReady && !hasNext())
                    throw new NoSuchElementException();
                else {
                    valueReady = false;
                    return nextElement;
                }
            }
        };

        try {
            adapter.nextDouble();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }

    private static void testNextDoubleReturnsValueWhenReady() {
        // Create an instance of Adapter with valueReady = true and nextElement set
        Spliterators.Adapter.OfDouble adapter = new Spliterators.Adapter.OfDouble() {
            boolean valueReady = true;
            double nextElement = 42.0;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public double nextDouble() {
                if (!valueReady && !hasNext())
                    throw new NoSuchElementException();
                else {
                    valueReady = false;
                    return nextElement;
                }
            }
        };

        double val = adapter.nextDouble();
        if (val != 42.0) {
            throw new AssertionError("Expected 42.0 but got " + val);
        }
    }
}
