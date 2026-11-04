/*
 * @test
 * @summary Tests for java.lang.Thread.setName(String)
 * @run main ThreadWitupTest
 */

import java.lang.Thread;

public class ThreadWitupTest {

    public static void main(String[] args) {
        testSetNameWithValidName();
        testSetNameWithNull();
    }

    private static void testSetNameWithValidName() {
        Thread thread = new Thread();
        String validName = "TestThread";
        thread.setName(validName);
        assert thread.getName().equals(validName) : "Expected thread name to be '" + validName + "'";
    }

    private static void testSetNameWithNull() {
        Thread thread = new Thread();
        try {
            thread.setName(null);
            assert false : "Expected NullPointerException when setting null name";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("name cannot be null") : "Unexpected exception message";
        }
    }
}
