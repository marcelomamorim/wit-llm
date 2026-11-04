/*
 * @test
 * @summary Test java.lang.Thread.setName(String) for null argument and normal usage
 * @run main ThreadWitupTest
 */

public class ThreadWitupTest {
    public static void main(String[] args) {
        testSetNameWithNull();
        testSetNameWithValidName();
    }

    private static void testSetNameWithNull() {
        Thread t = new Thread();
        try {
            t.setName(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            String msg = npe.getMessage();
            if (msg == null || !msg.contains("name cannot be null")) {
                throw new RuntimeException("NullPointerException message unexpected: " + msg);
            }
        }
    }

    private static void testSetNameWithValidName() {
        Thread t = new Thread();
        String newName = "TestThreadName";
        t.setName(newName);
        String actualName = t.getName();
        if (!newName.equals(actualName)) {
            throw new RuntimeException("Thread name was not set correctly. Expected: " + newName + ", got: " + actualName);
        }
    }
}
