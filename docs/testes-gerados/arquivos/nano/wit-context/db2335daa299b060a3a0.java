/* @test    @summary Test for TreeMap.comparator() method to observe behavior and exception handling    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.Comparator;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testComparatorMethod();
    }

    public static void testComparatorMethod() {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            Comparator<? super Integer> comp = map.comparator();
            System.out.println("Comparator: " + comp);
        } catch (InternalError e) {
            System.out.println("Caught expected InternalError from comparator() method.");
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }
}
