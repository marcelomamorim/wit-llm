/* @test    @summary Test for AbstractMap.setValue(V) method to verify UnsupportedOperationException is thrown.    @run main AbstractMapWitupTest */

import java.util.AbstractMap;

public class AbstractMapWitupTest {

    public static void main(String[] args) {
        testSetValueThrowsException();
    }

    public static void testSetValueThrowsException() {
        AbstractMap.SimpleImmutableEntry<String, String> entry = new AbstractMap.SimpleImmutableEntry<>("key", "value");
        try {
            entry.setValue("newValue");
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed: UnsupportedOperationException was thrown as expected.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
            System.exit(1);
        }
    }
}
