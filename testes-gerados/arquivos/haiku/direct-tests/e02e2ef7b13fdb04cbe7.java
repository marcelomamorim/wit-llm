/*
 * @test
 * @summary Verifies Optional.get() returns the contained value and throws NoSuchElementException when empty
 * @run main OptionalWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testGetReturnsPresentValue();
        testGetReturnsSameReferenceForObject();
        testGetReturnsNullValueOfOfNullableIsNotAllowed();
        testGetOnEmptyThrowsNoSuchElementException();
        testGetOnEmptyAfterEmptyFactoryThrows();
    }

    static void testGetReturnsPresentValue() {
        Optional<String> opt = Optional.of("abc");
        String v = opt.get();
        if (!"abc".equals(v)) {
            throw new AssertionError("expected abc but was " + v);
        }
    }

    static void testGetReturnsSameReferenceForObject() {
        Object o = new Object();
        Optional<Object> opt = Optional.of(o);
        if (opt.get() != o) {
            throw new AssertionError("get() must return the same reference");
        }
    }

    static void testGetReturnsNullValueOfOfNullableIsNotAllowed() {
        boolean thrown = false;
        try {
            Optional.of(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Optional.of(null) must throw NullPointerException");
        }
    }

    static void testGetOnEmptyThrowsNoSuchElementException() {
        Optional<String> opt = Optional.empty();
        boolean thrown = false;
        try {
            opt.get();
        } catch (NoSuchElementException e) {
            thrown = true;
            if (!"No value present".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("get() on empty must throw NoSuchElementException");
        }
    }

    static void testGetOnEmptyAfterEmptyFactoryThrows() {
        Optional<Integer> opt = Optional.empty();
        boolean thrown = false;
        try {
            opt.get();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("get() on empty must throw");
        }
    }
}
