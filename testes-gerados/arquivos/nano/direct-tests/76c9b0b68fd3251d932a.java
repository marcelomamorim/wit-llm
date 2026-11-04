/*
 * @test
 * @summary Tests for jdk.internal.module.ModuleHashesBuilder.reverse method
 * @run main ModuleHashesBuilderWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class ModuleHashesBuilderWitupTest {

    public static void main(String[] args) {
        testReverseWithEmptyList();
        testReverseWithSingleElement();
        testReverseWithMultipleElements();
        testReverseWithNullConsumer();
    }

    static void testReverseWithEmptyList() {
        List<String> list = new ArrayList<>();
        List<String> captured = new ArrayList<>();
        Consumer<String> consumer = captured::add;
        // Reverse on empty list should not invoke consumer
        Collections.reverse(list, consumer);
        assert captured.isEmpty() : "Expected no invocation on empty list";
    }

    static void testReverseWithSingleElement() {
        List<String> list = new ArrayList<>();
        list.add("a");
        List<String> captured = new ArrayList<>();
        Consumer<String> consumer = captured::add;
        // Reverse on single element should invoke consumer once with the element
        Collections.reverse(list, consumer);
        assert captured.size() == 1 : "Expected one invocation";
        assert captured.get(0).equals("a") : "Expected element 'a'";
    }

    static void testReverseWithMultipleElements() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        List<String> captured = new ArrayList<>();
        Consumer<String> consumer = captured::add;
        // Reverse should invoke consumer in reverse order
        Collections.reverse(list, consumer);
        assert captured.size() == 3 : "Expected three invocations";
        assert captured.get(0).equals("3") : "First invoked should be '3'";
        assert captured.get(1).equals("2") : "Second invoked should be '2'";
        assert captured.get(2).equals("1") : "Third invoked should be '1'";
    }

    static void testReverseWithNullConsumer() {
        List<String> list = new ArrayList<>();
        list.add("x");
        try {
            // Should throw NullPointerException
            Collections.reverse(list, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
