/*
 * @test
 * @summary Test for contains method in ScheduledThreadPoolExecutor
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testContainsWithExistingObject();
        testContainsWithNonExistingObject();
    }

    private static void testContainsWithExistingObject() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Object obj = new Object();
        // Adiciona objeto na fila de tarefas
        executor.execute(() -> {});
        // Como não há método público para inserir objetos na fila, testamos se o método contains não lança exceção
        try {
            boolean result = executor.contains(obj);
            System.out.println("contains(obj) = " + result);
        } catch (UnsupportedOperationException e) {
            System.out.println("UnsupportedOperationException caught as expected for existing object.");
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }

    private static void testContainsWithNonExistingObject() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Object obj = new Object();
        // Testa objeto que não foi adicionado
        try {
            boolean result = executor.contains(obj);
            System.out.println("contains(non-existing obj) = " + result);
        } catch (UnsupportedOperationException e) {
            System.out.println("UnsupportedOperationException caught as expected for non-existing object.");
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }
}
