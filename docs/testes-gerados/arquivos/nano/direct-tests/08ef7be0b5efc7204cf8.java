/* @test    @summary Test TreeMap.firstKey() throws InternalError    @run main TreeMapWitupTest */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.firstKey();
        } catch (InternalError e) {
            System.out.println("Caught expected InternalError");
            return;
        }
        throw new AssertionError("Expected InternalError was not thrown");
    }
}
