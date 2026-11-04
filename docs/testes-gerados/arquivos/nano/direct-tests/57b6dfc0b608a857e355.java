/* @test    @summary Test getAsLong() method of OptionalLong for present and absent cases    @run main OptionalLongWitupTest */

import java.util.OptionalLong;
import java.util.NoSuchElementException;

public class OptionalLongWitupTest {

    public static void main(String[] args) {
        testGetAsLongWithValue();
        testGetAsLongWithoutValue();
    }

    public static void testGetAsLongWithValue() {
        OptionalLong opt = OptionalLong.of(123L);
        long result = opt.getAsLong();
        if (result != 123L) {
            throw new AssertionError("Expected 123L, but got " + result);
        }
    }

    public static void testGetAsLongWithoutValue() {
        OptionalLong opt = OptionalLong.empty();
        try {
            opt.getAsLong();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // expected exception
        }
    }
}
