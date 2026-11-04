/*
 * @test
 * @summary Test IntStream.iterate method for deterministic behavior
 * @run main IntStreamWitupTest
 */

import java.util.stream.IntStream;
import java.util.function.IntUnaryOperator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class IntStreamWitupTest {
    public static void main(String[] args) {
        testIterateSimpleSequence();
        testIterateWithIdentityFunction();
        testIterateWithNegativeSeed();
        testIterateWithNullFunction();
    }

    private static void testIterateSimpleSequence() {
        IntUnaryOperator f = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand + 1;
            }
        };
        IntStream stream = IntStream.iterate(0, f);
        int[] expected = {0, 1, 2, 3, 4};
        int[] actual = stream.limit(5).toArray();
        if (actual.length != expected.length) {
            throw new AssertionError("Length mismatch: expected " + expected.length + " but got " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("Element mismatch at index " + i + ": expected " + expected[i] + " but got " + actual[i]);
            }
        }
    }

    private static void testIterateWithIdentityFunction() {
        IntUnaryOperator identity = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand;
            }
        };
        IntStream stream = IntStream.iterate(42, identity);
        int[] actual = stream.limit(3).toArray();
        int[] expected = {42, 42, 42};
        if (actual.length != expected.length) {
            throw new AssertionError("Length mismatch: expected " + expected.length + " but got " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("Element mismatch at index " + i + ": expected " + expected[i] + " but got " + actual[i]);
            }
        }
    }

    private static void testIterateWithNegativeSeed() {
        IntUnaryOperator decrement = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand - 1;
            }
        };
        IntStream stream = IntStream.iterate(-3, decrement);
        int[] expected = {-3, -4, -5, -6, -7};
        int[] actual = stream.limit(5).toArray();
        if (actual.length != expected.length) {
            throw new AssertionError("Length mismatch: expected " + expected.length + " but got " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("Element mismatch at index " + i + ": expected " + expected[i] + " but got " + actual[i]);
            }
        }
    }

    private static void testIterateWithNullFunction() {
        try {
            IntStream.iterate(0, null);
            throw new AssertionError("Expected NullPointerException for null function");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
