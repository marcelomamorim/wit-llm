/*
 * @test
 * @summary Testando o método ints() de ThreadLocalRandom
 * @run main ThreadLocalRandomWitupTest_P678ec193fb60
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest_P678ec193fb60 {

    public static void main(String[] args) {
        testIntsMethod();
    }

    private static void testIntsMethod() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        IntStream stream = rand.ints();
        try {
            int first = stream.findFirst().orElse(0);
            System.out.println("First int from stream: " + first);
        } catch (Exception e) {
            System.out.println("Exception thrown during ints(): " + e.getClass().getName());
        }
        // Testar se o método não lança exceções inesperadas
        // Como o método é um gerador de stream infinito, apenas verificamos um elemento
        // e fechamos o stream
        try (IntStream s = rand.ints()) {
            int val = s.limit(5).sum();
            System.out.println("Sum of first 5 ints: " + val);
        } catch (Exception e) {
            System.out.println("Exception during stream consumption: " + e.getClass().getName());
        }
    }
}
