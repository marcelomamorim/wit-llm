/*
 * @test
 * @summary Witup-derived checks for OptionalInt.orElseThrow() returning values and throwing NoSuchElementException when empty
 * @run main OptionalIntWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalInt;

public class OptionalIntWitupTest {
    public static void main(String[] args) {
        if (OptionalInt.of(5).orElseThrow() != 5) {
            throw new RuntimeException("of(5).orElseThrow() should return 5");
        }
        if (OptionalInt.of(0).orElseThrow() != 0) {
            throw new RuntimeException("of(0).orElseThrow() should return 0");
        }
        if (OptionalInt.of(-1).orElseThrow() != -1) {
            throw new RuntimeException("of(-1).orElseThrow() should return -1");
        }
        if (OptionalInt.of(Integer.MIN_VALUE).orElseThrow() != Integer.MIN_VALUE) {
            throw new RuntimeException("of(MIN_VALUE).orElseThrow() should return MIN_VALUE");
        }
        if (OptionalInt.of(Integer.MAX_VALUE).orElseThrow() != Integer.MAX_VALUE) {
            throw new RuntimeException("of(MAX_VALUE).orElseThrow() should return MAX_VALUE");
        }
        try {
            OptionalInt.empty().orElseThrow();
            throw new RuntimeException("empty().orElseThrow() should throw NoSuchElementException");
        } catch (NoSuchElementException e) {
        }
    }
}
