/*
 * @test    @summary Test the next() method of CopyOnWriteArrayList to ensure it returns the next element or throws NoSuchElementException.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.NoSuchElementException;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testNextReturnsElement();
        testNextThrowsException();
    }

    static void testNextReturnsElement() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("element1");
        list.add("element2");
        list.add("element3");
        java.util.Iterator<String> it = list.iterator();
        String first = it.next();
        assert first.equals("element1") : "Expected element1, got " + first;
        String second = it.next();
        assert second.equals("element2") : "Expected element2, got " + second;
        String third = it.next();
        assert third.equals("element3") : "Expected element3, got " + third;
    }

    static void testNextThrowsException() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("onlyElement");
        java.util.Iterator<String> it = list.iterator();
        it.next(); // consume the only element
        boolean exceptionThrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NoSuchElementException to be thrown";
    }
}
