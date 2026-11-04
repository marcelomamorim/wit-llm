/* @test    @summary Test for TreeMap.firstKey() method to observe behavior and exception handling    @run main TreeMapWitupTest */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testFirstKeyThrowsInternalError();
    }

    private static void testFirstKeyThrowsInternalError() {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.firstKey();
            System.out.println("Test failed: Expected InternalError was not thrown.");
        } catch (InternalError e) {
            System.out.println("Test passed: Caught expected InternalError.");
        } catch (Throwable t) {
            System.out.println("Test failed: Caught unexpected exception: " + t);
        }
    }
}
