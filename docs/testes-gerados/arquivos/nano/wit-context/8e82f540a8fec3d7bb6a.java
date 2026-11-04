/*
 * @test
 * @summary Test for java.util.Spliterators.next() method
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNextThrowsWhenValueNotReady();
        testNextReturnsNextElementWhenValueReady();
    }

    private static void testNextThrowsWhenValueNotReady() {
        Spliterators.Adapter<Integer> adapter = new Spliterators.Adapter<Integer>() {
            boolean valueReady = false;
            Integer nextElement = 42;

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                if (!valueReady && !hasNext()) {
                    throw new java.util.NoSuchElementException();
                } else {
                    valueReady = false;
                    return nextElement;
                }
            }
        };
        try {
            adapter.next();
            // Fail if no exception
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (java.util.NoSuchElementException e) {
            // Expected exception
        }
    }

    private static void testNextReturnsNextElementWhenValueReady() {
        Spliterators.Adapter<Integer> adapter = new Spliterators.Adapter<Integer>() {
            boolean valueReady = true;
            Integer nextElement = 99;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                if (!valueReady && !hasNext()) {
                    throw new java.util.NoSuchElementException();
                } else {
                    valueReady = false;
                    return nextElement;
                }
            }
        };
        Integer result = adapter.next();
        if (result == null || result != 99) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }
}
