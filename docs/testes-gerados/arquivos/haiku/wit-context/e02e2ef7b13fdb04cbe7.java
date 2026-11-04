/*
 * @test
 * @summary Verify Optional.get() returns the contained value and throws NoSuchElementException when empty
 * @run main OptionalWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testGetReturnsPresentValue();
        testGetReturnsSameReference();
        testGetOnEmptyThrowsNoSuchElementException();
        testGetOnEmptyAfterOfNullableNullThrows();
    }

    static void testGetReturnsPresentValue() {
        Optional<String> opt = Optional.of("value");
        String result = opt.get();
        if (!"value".equals(result)) {
            throw new RuntimeException("Expected 'value' but got: " + result);
        }
    }

    static void testGetReturnsSameReference() {
        Object o = new Object();
        Optional<Object> opt = Optional.of(o);
        if (opt.get() != o) {
            throw new RuntimeException("get() did not return the same reference");
        }
    }

    static void testGetOnEmptyThrowsNoSuchElementException() {
        Optional<String> opt = Optional.empty();
        try {
            opt.get();
        } catch (NoSuchElementException e) {
            return;
        }
        throw new RuntimeException("Expected NoSuchElementException from get() on empty Optional");
    }

    static void testGetOnEmptyAfterOfNullableNullThrows() {
        Optional<String> opt = Optional.ofNullable(null);
        try {
            opt.get();
        } catch (NoSuchElementException e) {
            return;
        }
        throw new RuntimeException("Expected NoSuchElementException for ofNullable(null).get()");
    }
}
