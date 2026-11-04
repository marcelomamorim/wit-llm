/*
 * @test
 * @summary Test AtomicLongFieldUpdater.run() method for NullPointerException when fieldName is null
 * @run main AtomicLongFieldUpdaterWitupTest
 */

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class AtomicLongFieldUpdaterWitupTest {

    static class Dummy {
        volatile long value = 0L;
    }

    public static void main(String[] args) throws Exception {
        testRunWithNullFieldName();
        testRunWithValidFieldName();
    }

    // Test that run() throws NullPointerException if fieldName is null
    private static void testRunWithNullFieldName() {
        final AtomicLongFieldUpdater<Dummy> updater = new AtomicLongFieldUpdater<Dummy>() {
            final Class<Dummy> tclass = Dummy.class;
            final String fieldName = null;

            @Override
            public Field run() throws NoSuchFieldException {
                // This should throw NullPointerException because fieldName is null
                return tclass.getDeclaredField(fieldName);
            }

            @Override
            public boolean compareAndSet(Dummy obj, long expect, long update) {
                return false; // dummy
            }

            @Override
            public void set(Dummy obj, long newValue) {
                // dummy
            }

            @Override
            public long get(Dummy obj) {
                return 0L; // dummy
            }
        };

        try {
            updater.run();
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    // Test that run() returns the correct Field when fieldName is valid
    private static void testRunWithValidFieldName() throws Exception {
        final AtomicLongFieldUpdater<Dummy> updater = new AtomicLongFieldUpdater<Dummy>() {
            final Class<Dummy> tclass = Dummy.class;
            final String fieldName = "value";

            @Override
            public Field run() throws NoSuchFieldException {
                return tclass.getDeclaredField(fieldName);
            }

            @Override
            public boolean compareAndSet(Dummy obj, long expect, long update) {
                return false; // dummy
            }

            @Override
            public void set(Dummy obj, long newValue) {
                // dummy
            }

            @Override
            public long get(Dummy obj) {
                return 0L; // dummy
            }
        };

        Field f = updater.run();
        if (!"value".equals(f.getName())) {
            throw new RuntimeException("Field name mismatch: expected 'value' but got " + f.getName());
        }
    }
}
