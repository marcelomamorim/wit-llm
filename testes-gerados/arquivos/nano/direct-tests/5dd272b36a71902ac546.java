/* @test    @summary Test remove() method of PreHashedMap throws UnsupportedOperationException    @run main PreHashedMapWitupTest */

import sun.util.PreHashedMap;

public class PreHashedMapWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        try {
            // Instantiate with arbitrary parameters; actual init is not invoked here
            PreHashedMap<String> map = new PreHashedMap<String>(1, 0, 0, 0) {
                @Override
                protected void init(Object[] ht) {
                    // No initialization needed for this test
                }
            };
            // Call remove() and expect UnsupportedOperationException
            map.remove();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: remove() threw UnsupportedOperationException as expected.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
