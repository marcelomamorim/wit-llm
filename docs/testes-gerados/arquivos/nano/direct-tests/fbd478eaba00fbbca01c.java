/*
 * @test
 * @summary Test addAll method of FixedList to ensure it always throws UnsupportedOperationException
 * @run main FixedListWitupTest
 */

import com.sun.java.util.jar.pack.FixedList;
import java.util.Arrays;
import java.util.Collection;

public class FixedListWitupTest {

    public static void main(String[] args) {
        testAddAllThrowsException();
    }

    public static void testAddAllThrowsException() {
        FixedList<Integer> list = new FixedList<>(5);
        Collection<Integer> c = Arrays.asList(1, 2, 3);
        try {
            list.addAll(0, c);
            System.out.println("addAll did not throw exception as expected");
        } catch (UnsupportedOperationException e) {
            System.out.println("addAll correctly threw UnsupportedOperationException");
        } catch (Exception e) {
            System.out.println("addAll threw unexpected exception: " + e);
        }
    }
}
