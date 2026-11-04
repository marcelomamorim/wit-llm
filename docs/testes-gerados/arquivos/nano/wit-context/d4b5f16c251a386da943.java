/*
 * @test
 * @summary Test for FixedList.retainAll method to verify UnsupportedOperationException is thrown.
 * @run main FixedListWitupTest
 */

import com.sun.java.util.jar.pack.FixedList;
import java.util.Arrays;
import java.util.Collection;

public class FixedListWitupTest {

    public static void main(String[] args) {
        testRetainAllThrowsException();
    }

    public static void testRetainAllThrowsException() {
        FixedList<Object> fixedList = new FixedList<Object>(3);
        Collection<Object> dummyCollection = Arrays.asList(new Object());
        try {
            fixedList.retainAll(dummyCollection);
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException was correctly thrown.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
