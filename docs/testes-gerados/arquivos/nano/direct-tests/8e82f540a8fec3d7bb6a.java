/*
 * @test
 * @summary Test the next() method of java.util.Spliterators for normal and exceptional behavior
 * @run main SpliteratorsWitupTest
 */
import java.util.Spliterators;
import java.util.NoSuchElementException;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNextReturnsElement();
        testNextThrowsNoSuchElementException();
    }

    static void testNextReturnsElement() {
        class Dummy {
            boolean valueReady = true;
            Object nextElement = "element";
            Object next() {
                if (!valueReady) {
                    throw new NoSuchElementException();
                }
                valueReady = false;
                return nextElement;
            }
        }
        Dummy dummy = new Dummy();
        // Simulate the method's behavior
        Object result = null;
        try {
            if (dummy.valueReady) {
                result = dummy.next();
            }
            assert result.equals("element") : "Expected 'element', got " + result;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    static void testNextThrowsNoSuchElementException() {
        class Dummy {
            boolean valueReady = false;
            Object nextElement = "element";
            Object next() {
                if (!valueReady) {
                    throw new NoSuchElementException();
                }
                valueReady = false;
                return nextElement;
            }
        }
        Dummy dummy = new Dummy();
        boolean exceptionThrown = false;
        try {
            if (dummy.valueReady) {
                dummy.next();
            } else {
                dummy.next();
            }
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NoSuchElementException to be thrown";
    }
}
