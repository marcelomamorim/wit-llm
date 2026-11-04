/*
 * @test
 * @summary Test java.lang.Thread.setName(String) method behavior
 * @run main ThreadWitupTest
 */

public class ThreadWitupTest {
    public static void main(String[] args) {
        testSetNameWithValidName();
        testSetNameWithNullName();
    }

    private static void testSetNameWithValidName() {
        Thread t = new Thread();
        String newName = "MyThreadName";
        t.setName(newName);
        if (!t.getName().equals(newName)) {
            throw new RuntimeException("setName did not update the thread name correctly");
        }
    }

    private static void testSetNameWithNullName() {
        Thread t = new Thread();
        try {
            t.setName(null);
            throw new RuntimeException("setName(null) did not throw NullPointerException");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
