/*
 * @test
 * @summary Test the nextLong() method of java.util.Spliterators
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.NoSuchElementException;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNextLongReturnsNextElementWhenReady();
        testNextLongThrowsWhenNoMoreElements();
    }

    static void testNextLongReturnsNextElementWhenReady() {
        class DummySpliterator extends Spliterators {
            long nextElement = 42L;
            boolean valueReady = true;
            boolean hasNext() { return true; }
            long nextLong() {
                if (!valueReady && !hasNext())
                    throw new NoSuchElementException();
                else {
                    valueReady = false;
                    return nextElement;
                }
            }
        }
        DummySpliterator ds = new DummySpliterator();
        long result = ds.nextLong();
        assert result == 42L : "Expected 42L, got " + result;
    }

    static void testNextLongThrowsWhenNoMoreElements() {
        class DummySpliterator extends Spliterators {
            boolean valueReady = false;
            boolean hasNext() { return false; }
            long nextLong() {
                if (!valueReady && !hasNext())
                    throw new NoSuchElementException();
                else {
                    valueReady = false;
                    return 0L;
                }
            }
        }
        DummySpliterator ds = new DummySpliterator();
        try {
            ds.nextLong();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
