/* @test    @summary Test for CopyOnWriteArrayList.forEachRemaining method    @run main CopyOnWriteArrayListWitupTest */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingWithEmptyList();
    }

    static void testForEachRemainingWithElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        StringBuilder result = new StringBuilder();
        list.forEachRemaining(new Consumer<String>() {
            @Override
            public void accept(String s) {
                result.append(s);
            }
        });
        if (!"abc".equals(result.toString())) {
            throw new AssertionError("Expected 'abc', but got: " + result);
        }
    }

    static void testForEachRemainingWithEmptyList() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        StringBuilder result = new StringBuilder();
        list.forEachRemaining(new Consumer<Integer>() {
            @Override
            public void accept(Integer i) {
                result.append(i);
            }
        });
        if (result.length() != 0) {
            throw new AssertionError("Expected empty result, but got: " + result);
        }
    }
}
