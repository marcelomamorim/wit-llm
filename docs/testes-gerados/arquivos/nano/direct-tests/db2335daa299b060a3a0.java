/* @test    @summary Test TreeMap.comparator() method for exception throwing and behavior.    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.Comparator;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testComparatorThrowsInternalError();
    }

    private static void testComparatorThrowsInternalError() {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.comparator();
            // Since the method throws InternalError, reaching this line indicates failure.
            System.err.println("Expected InternalError was not thrown.");
            System.exit(1);
        } catch (InternalError e) {
            // Expected exception caught, test passes.
            System.out.println("InternalError correctly thrown by comparator().");
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
