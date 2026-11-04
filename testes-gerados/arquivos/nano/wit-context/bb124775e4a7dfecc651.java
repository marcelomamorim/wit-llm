/* @test    @summary Testa o construtor de LinkedBlockingDeque com capacidade inválida.    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testConstructorWithZeroCapacity();
        testConstructorWithNegativeCapacity();
        testConstructorWithValidCapacity();
    }

    private static void testConstructorWithZeroCapacity() {
        try {
            new LinkedBlockingDeque(0);
            System.err.println("Falha: Esperado IllegalArgumentException para capacidade 0");
        } catch (IllegalArgumentException e) {
            System.out.println("Sucesso: IllegalArgumentException lançado para capacidade 0");
        }
    }

    private static void testConstructorWithNegativeCapacity() {
        try {
            new LinkedBlockingDeque(-1);
            System.err.println("Falha: Esperado IllegalArgumentException para capacidade negativa");
        } catch (IllegalArgumentException e) {
            System.out.println("Sucesso: IllegalArgumentException lançado para capacidade negativa");
        }
    }

    private static void testConstructorWithValidCapacity() {
        try {
            LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(10);
            if (deque != null) {
                System.out.println("Sucesso: deque criado com capacidade válida");
            } else {
                System.err.println("Falha: deque nulo com capacidade válida");
            }
        } catch (Exception e) {
            System.err.println("Falha: Exceção inesperada ao criar deque com capacidade válida: " + e);
        }
    }
}
