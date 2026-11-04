/* @test    @summary Test for OptionalLong.getAsLong() method to verify exception when no value is present    @run main OptionalLongWitupTest */

import java.util.OptionalLong;

public class OptionalLongWitupTest {

    public static void main(String[] args) {
        testGetAsLongThrowsExceptionWhenEmpty();
        testGetAsLongReturnsValueWhenPresent();
    }

    private static void testGetAsLongThrowsExceptionWhenEmpty() {
        OptionalLong opt = OptionalLong.empty();
        boolean exceptionThrown = false;
        try {
            opt.getAsLong();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException when calling getAsLong() on empty OptionalLong");
        }
    }

    private static void testGetAsLongReturnsValueWhenPresent() {
        long expectedValue = 42L;
        OptionalLong opt = OptionalLong.of(expectedValue);
        long actualValue = opt.getAsLong();
        if (actualValue != expectedValue) {
            throw new AssertionError("Expected value: " + expectedValue + ", but got: " + actualValue);
        }
    }
}
