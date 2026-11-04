/* @test    @summary Test Thread.setName with valid and null input    @run main ThreadWitupTest */

import java.lang.Thread;

public class ThreadWitupTest {

    public static void main(String[] args) {
        testSetNameValid();
        testSetNameNull();
    }

    private static void testSetNameValid() {
        Thread t = new Thread();
        String newName = "TestThread";
        t.setName(newName);
        assert t.getName().equals(newName) : "Expected thread name to be '" + newName + "' but was '" + t.getName() + "'";
    }

    private static void testSetNameNull() {
        Thread t = new Thread();
        try {
            t.setName(null);
            assert false : "Expected NullPointerException when setting null name";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("name cannot be null") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
