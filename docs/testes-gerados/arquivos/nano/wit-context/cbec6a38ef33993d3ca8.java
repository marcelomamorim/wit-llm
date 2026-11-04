/*
 * @test
 * @summary Test for Collections.forEachRemaining with null Consumer
 * @run main CollectionsWitupTest
 */

import java.util.*;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingValidAction();
    }

    private static void testForEachRemainingNullAction() {
        List<String> list = Arrays.asList("a", "b", "c");
        Iterator<String> iterator = list.iterator();
        try {
            // Invoca o método com action == null, esperando NullPointerException
            iterator.forEachRemaining(null);
            // Se não lançar exceção, falha o teste
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Teste passou
        }
    }

    private static void testForEachRemainingValidAction() {
        List<String> list = Arrays.asList("a", "b", "c");
        List<String> result = new ArrayList<>();
        Iterator<String> iterator = list.iterator();
        Consumer<? super String> consumer = result::add;
        iterator.forEachRemaining(consumer);
        // Verifica se todos os elementos foram consumidos
        if (!result.containsAll(list) || result.size() != list.size()) {
            throw new AssertionError("Not all elements processed: " + result);
        }
    }
}
