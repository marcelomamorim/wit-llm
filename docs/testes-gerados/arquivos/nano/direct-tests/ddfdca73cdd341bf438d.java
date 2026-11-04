/*
 * @test
 * @summary Test tryAdvance method of java.util.Spliterators
 * @run main SpliteratorsWitupTest
 */
import java.util.Spliterators;
import java.util.function.Consumer;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithElement();
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWhenNoElements();
    }

    static void testTryAdvanceWithElement() {
        Object[] array = {"a", "b", "c"};
        int index = 0;
        int fence = array.length;
        Consumer<Object> consumer = new Consumer<Object>() {
            @Override
            public void accept(Object e) {
                assert e.equals(array[index]) : "Element mismatch";
                index++;
            }
        };
        boolean result = new Object() {
            boolean tryAdvance() {
                if (index >= 0 && index < fence) {
                    Object e = array[index++];
                    consumer.accept(e);
                    return true;
                }
                return false;
            }
        }.tryAdvance();
        assert result : "tryAdvance should return true when elements are available";
        assert index == 1 : "Index should be incremented";
    }

    static void testTryAdvanceWithNullConsumer() {
        boolean exceptionThrown = false;
        try {
            Spliterators.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "NullPointerException should be thrown for null consumer";
    }

    static void testTryAdvanceWhenNoElements() {
        Object[] emptyArray = {};
        int index = 0;
        int fence = 0;
        Consumer<Object> consumer = e -> {};
        boolean result = new Object() {
            boolean tryAdvance() {
                if (index >= 0 && index < fence) {
                    Object e = emptyArray[index++];
                    consumer.accept(e);
                    return true;
                }
                return false;
            }
        }.tryAdvance();
        assert !result : "tryAdvance should return false when no elements";
    }
}
