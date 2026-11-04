/*
 * @test
 * @summary Verifies the iterator returned by Arrays.asList(...) (whose next() is the ArrayItr.next() target) returns elements in order and throws NoSuchElementException when exhausted
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArraysWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        String[] a = {"x", "y"};
        Iterator<String> it = Arrays.asList(a).iterator();
        check(it.hasNext(), "hasNext should be true at start");
        check("x".equals(it.next()), "first element should be x");
        check("y".equals(it.next()), "second element should be y");
        check(!it.hasNext(), "hasNext should be false after last element");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() past end should throw NoSuchElementException");

        Iterator<Integer> empty = Arrays.asList(new Integer[0]).iterator();
        thrown = false;
        try {
            empty.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on empty array iterator should throw NoSuchElementException");

        Iterator<String> nulls = Arrays.asList(new String[]{null}).iterator();
        check(nulls.next() == null, "null element should be returned as null");
        thrown = false;
        try {
            nulls.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() after null element should throw NoSuchElementException");
    }
}
