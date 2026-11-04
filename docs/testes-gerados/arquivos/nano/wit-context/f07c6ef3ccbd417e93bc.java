/*
 * @test
 * @summary Test for ConcurrentHashMap.forEachRemaining with null action
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
    }

    public static void testForEachRemainingNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        // Obtém um iterator para o método forEachRemaining
        java.util.Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        // Avança o iterator uma vez para preparar para forEachRemaining
        iterator.next();

        // Cria um Consumer nulo para passar ao método
        Consumer<? super Map.Entry<String, String>> nullConsumer = null;

        // Invoca o método e verifica se lança NullPointerException
        try {
            // Como o método é protegido por um iterator, simula a chamada direta
            // obtendo o método via reflexão
            java.lang.reflect.Method method = ConcurrentHashMap.class.getMethod("forEachRemaining", Consumer.class);
            // Invoca o método com consumer nulo
            method.invoke(map.entrySet().iterator(), nullConsumer);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            // Verifica se a causa é NullPointerException
            if (!(e.getCause() instanceof NullPointerException)) {
                throw new AssertionError("Unexpected exception cause: " + e.getCause(), e);
            }
            // Exceção esperada, o teste passa
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new AssertionError("Reflection error during test", e);
        }
    }
}
