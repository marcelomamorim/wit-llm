/*
 * @test
 * @summary Testa o construtor de LinkedBlockingQueue com capacidade inválida.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testConstructorWithZeroCapacity();
        testConstructorWithNegativeCapacity();
    }

    private static void testConstructorWithZeroCapacity() {
        try {
            new LinkedBlockingQueue(0);
            System.err.println("Falha: Esperado IllegalArgumentException para capacidade 0.");
        } catch (IllegalArgumentException e) {
            System.out.println("Sucesso: Exceção lançada para capacidade 0.");
        } catch (Exception e) {
            System.err.println("Falha: Exceção inesperada para capacidade 0: " + e);
        }
    }

    private static void testConstructorWithNegativeCapacity() {
        try {
            new LinkedBlockingQueue(-1);
            System.err.println("Falha: Esperado IllegalArgumentException para capacidade negativa.");
        } catch (IllegalArgumentException e) {
            System.out.println("Sucesso: Exceção lançada para capacidade negativa.");
        } catch (Exception e) {
            System.err.println("Falha: Exceção inesperada para capacidade negativa: " + e);
        }
    }
}
