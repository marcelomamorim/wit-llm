/*
 * @test
 * @summary Witup-generated tests for OptionalInt.getAsInt()
 * @run main OptionalIntWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalInt;

public class OptionalIntWitupTest {

    public static void main(String[] args) {
        testGetAsIntPresent();
        testGetAsIntOfZero();
        testGetAsIntOfNegative();
        testGetAsIntEmptyThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testGetAsIntPresent() {
        check(OptionalInt.of(42).getAsInt() == 42, "getAsInt() should return 42");
    }

    static void testGetAsIntOfZero() {
        check(OptionalInt.of(0).getAsInt() == 0, "getAsInt() should return 0");
    }

    static void testGetAsIntOfNegative() {
        check(OptionalInt.of(-7).getAsInt() == -7, "getAsInt() should return -7");
    }

    static void testGetAsIntEmptyThrows() {
        boolean thrown = false;
        try {
            OptionalInt.empty().getAsInt();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "getAsInt() on empty OptionalInt should throw NoSuchElementException");
    }
}
