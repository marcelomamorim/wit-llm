/*
 * @test
 * @summary Test for ArrayList.retainAll(Collection<?>) method to observe exception behavior.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNullCollection();
        testRetainAllWithConcurrentModification();
    }

    private static void testRetainAllWithNullCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            list.retainAll(null);
            System.out.println("retainAll with null collection did not throw");
        } catch (NullPointerException e) {
            System.out.println("Expected NullPointerException caught");
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }

    private static void testRetainAllWithConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Collection<String> c = new ArrayList<>();
        c.add("a");
        c.add("b");
        try {
            for (String s : list) {
                list.remove(s);
                break;
            }
            list.retainAll(c);
            System.out.println("retainAll with concurrent modification did not throw");
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Expected ConcurrentModificationException caught");
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }
}
