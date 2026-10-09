/* @test    @summary Testa o método nextInt com limites válidos e inválidos em ThreadLocalRandom    @run main ThreadLocalRandomWitupTest_P744965ea1631 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P744965ea1631 {
    public static void main(String[] args) {
        testNextIntValidRange();
        testNextIntInvalidRange();
    }

    static void testNextIntValidRange() {
        try {
            int origin = 10;
            int bound = 20;
            int result = ThreadLocalRandom.current().nextInt(origin, bound);
            if (result < origin || result >= bound) {
                throw new AssertionError("Result out of range: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid range test: " + e);
        }
    }

    static void testNextIntInvalidRange() {
        try {
            int origin = 20;
            int bound = 10;
            ThreadLocalRandom.current().nextInt(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException not thrown for invalid range");
        } catch (IllegalArgumentException e) {
            // esperado
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
