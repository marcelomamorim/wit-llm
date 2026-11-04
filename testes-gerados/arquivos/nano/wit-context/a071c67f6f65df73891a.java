/* @test    @summary Test for OptionalLong.orElseThrow() method behavior.    @run main OptionalLongWitupTest */

import java.util.OptionalLong;

public class OptionalLongWitupTest {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    private static void testOrElseThrowWithValue() {
        OptionalLong opt = OptionalLong.of(42L);
        long result = opt.orElseThrow();
        if (result != 42L) {
            throw new AssertionError("Expected 42, but got " + result);
        }
    }

    private static void testOrElseThrowWithoutValue() {
        OptionalLong opt = OptionalLong.empty();
        try {
            opt.orElseThrow();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
