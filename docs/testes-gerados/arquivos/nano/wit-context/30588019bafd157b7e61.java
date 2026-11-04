/* @test    @summary Testa o método orElseThrow() de Optional para comportamento esperado em JDK 11+28    @run main OptionalWitupTest */

import java.util.Optional;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    private static void testOrElseThrowWithValue() {
        Optional<String> opt = Optional.of("test");
        String result = opt.orElseThrow();
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test', got: " + result);
        }
    }

    private static void testOrElseThrowWithoutValue() {
        Optional<String> emptyOpt = Optional.empty();
        try {
            emptyOpt.orElseThrow();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
