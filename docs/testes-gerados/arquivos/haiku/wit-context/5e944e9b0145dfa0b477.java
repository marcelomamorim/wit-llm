/*
 * @test
 * @summary Verifies Thread.setName rejects null names and stores valid names observable through getName
 * @run main ThreadWitupTest
 */

public class ThreadWitupTest {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread();
        String before = t.getName();

        try {
            t.setName(null);
            throw new AssertionError("expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }

        if (!before.equals(t.getName())) {
            throw new AssertionError("name changed after rejected setName(null)");
        }

        t.setName("alpha");
        if (!"alpha".equals(t.getName())) {
            throw new AssertionError("expected name alpha but was " + t.getName());
        }

        Thread started = new Thread(new Runnable() {
            public void run() {
            }
        }, "initial");
        started.start();
        started.setName("beta");
        started.join();
        if (!"beta".equals(started.getName())) {
            throw new AssertionError("expected name beta but was " + started.getName());
        }

        try {
            started.setName(null);
            throw new AssertionError("expected NullPointerException for null name on started thread");
        } catch (NullPointerException expected) {
            // expected
        }
        if (!"beta".equals(started.getName())) {
            throw new AssertionError("name changed after rejected setName(null) on started thread");
        }
    }
}
