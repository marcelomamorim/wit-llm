/*
 * @test
 * @summary Witup-generated tests for OptionalLong.getAsLong()
 * @run main OptionalLongWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalLong;

public class OptionalLongWitupTest {

    public static void main(String[] args) {
        testGetAsLongPresent();
        testGetAsLongEmptyThrows();
        testGetAsLongZeroValue();
        testGetAsLongNegativeValue();
        testGetAsLongMaxValue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testGetAsLongPresent() {
        OptionalLong o = OptionalLong.of(42L);
        check(o.getAsLong() == 42L, "getAsLong() should return the stored value");
    }

    static void testGetAsLongEmptyThrows() {
        OptionalLong o = OptionalLong.empty();
        try {
            o.getAsLong();
            throw new RuntimeException("getAsLong() on empty should throw NoSuchElementException");
        } catch (NoSuchElementException expected) {
            check("No value present".equals(expected.getMessage()),
                  "unexpected message: " + expected.getMessage());
        }
    }

    static void testGetAsLongZeroValue() {
        OptionalLong o = OptionalLong.of(0L);
        check(o.getAsLong() == 0L, "zero value must be present and returned");
    }

    static void testGetAsLongNegativeValue() {
        OptionalLong o = OptionalLong.of(-1L);
        check(o.getAsLong() == -1L, "negative value must be returned unchanged");
    }

    static void testGetAsLongMaxValue() {
        OptionalLong o = OptionalLong.of(Long.MAX_VALUE);
        check(o.getAsLong() == Long.MAX_VALUE, "Long.MAX_VALUE must be returned unchanged");
    }
}
