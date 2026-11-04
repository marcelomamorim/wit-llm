/* @test    @summary Test tryAdvance method of ArrayList for normal and boundary behavior    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.function.Consumer;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithElement();
        testTryAdvanceWhenEmpty();
        testTryAdvanceMultipleCalls();
    }

    static void testTryAdvanceWithElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("element");
        final boolean[] called = {false};
        Consumer<String> consumer = e -> {
            assert "element".equals(e) : "Expected element to be 'element'";
            called[0] = true;
        };
        boolean result = list.tryAdvance(consumer);
        assert result : "tryAdvance should return true when element exists";
        assert called[0] : "Consumer should be called with the element";
    }

    static void testTryAdvanceWhenEmpty() {
        ArrayList<String> list = new ArrayList<>();
        final boolean[] called = {false};
        Consumer<String> consumer = e -> {
            called[0] = true;
        };
        boolean result = list.tryAdvance(consumer);
        assert !result : "tryAdvance should return false when list is empty";
        assert !called[0] : "Consumer should not be called when list is empty";
    }

    static void testTryAdvanceMultipleCalls() {
        ArrayList<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");
        final String[] received = {null, null};
        Consumer<String> consumer1 = e -> received[0] = e;
        Consumer<String> consumer2 = e -> received[1] = e;
        boolean firstCall = list.tryAdvance(consumer1);
        boolean secondCall = list.tryAdvance(consumer2);
        boolean thirdCall = list.tryAdvance(e -> {});
        assert firstCall : "First tryAdvance should return true";
        assert secondCall : "Second tryAdvance should return true";
        assert !thirdCall : "Third tryAdvance should return false when no more elements";
        assert "first".equals(received[0]) : "First element received should be 'first'";
        assert "second".equals(received[1]) : "Second element received should be 'second'";
    }
}
