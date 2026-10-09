/*
 * @test
 * @summary Test for OptionalInt.orElseThrow method behavior
 * @run main OptionalIntWitupTest_P8b59b555bfbf
 */

import java.util.OptionalInt;

public class OptionalIntWitupTest_P8b59b555bfbf {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValueThrows();
        System.out.println("All tests passed.");
    }

    static void testOrElseThrowWithValue() {
        OptionalInt opt = OptionalInt.of(42);
        int result = opt.orElseThrow(() -> new RuntimeException("No value"));
        assert result == 42 : "Expected 42, got " + result;
    }

    static void testOrElseThrowWithoutValueThrows() {
        OptionalInt opt = OptionalInt.empty();
        boolean threw = false;
        try {
            opt.orElseThrow(() -> new RuntimeException("No value"));
        } catch (RuntimeException e) {
            threw = true;
            assert e.getMessage().equals("No value") : "Unexpected message: " + e.getMessage();
        }
        assert threw : "Expected RuntimeException to be thrown";
    }
}
