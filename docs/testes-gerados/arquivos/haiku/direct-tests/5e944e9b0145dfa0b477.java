/*
 * @test
 * @summary Verifies Thread.setName observable behavior: name update, null rejection and renaming after start/termination
 * @run main ThreadWitupTest
 */

public class ThreadWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Thread t = new Thread("initial");
        check(t.getName().equals("initial"), "initial name not set");

        t.setName("renamed");
        check(t.getName().equals("renamed"), "setName did not update name");

        try {
            t.setName(null);
            throw new RuntimeException("setName(null) did not throw NullPointerException");
        } catch (NullPointerException e) {
            check("name cannot be null".equals(e.getMessage()),
                  "unexpected NPE message: " + e.getMessage());
        }
        check(t.getName().equals("renamed"), "name changed after rejected null");

        Thread s = new Thread(() -> { });
        s.setName("beforeStart");
        s.start();
        s.join();
        check(s.getName().equals("beforeStart"), "name lost after thread run");

        s.setName("afterRun");
        check(s.getName().equals("afterRun"), "setName after termination failed");
    }
}
