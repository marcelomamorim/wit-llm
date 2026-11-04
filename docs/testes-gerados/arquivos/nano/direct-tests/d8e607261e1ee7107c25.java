/*
 * @test    @summary Test for CopyOnWriteArrayList forEachRemaining method
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingWithEmptyList();
    }

    static void testForEachRemainingWithElements() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        StringBuilder result = new StringBuilder();
        list.forEachRemaining(new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                result.append(t);
            }
        });
        assert result.toString().equals("123") : "Expected '123', but got '" + result.toString() + "'";
    }

    static void testForEachRemainingWithEmptyList() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        StringBuilder result = new StringBuilder();
        list.forEachRemaining(new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                result.append(t);
            }
        });
        assert result.toString().isEmpty() : "Expected empty string, but got '" + result.toString() + "'";
    }
}
