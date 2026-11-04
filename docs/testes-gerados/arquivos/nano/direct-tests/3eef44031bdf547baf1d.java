/*
 * @test
 * @summary Test for AtomicReferenceFieldUpdater.run() method
 * @run main AtomicReferenceFieldUpdaterWitupTest
 */

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdaterWitupTest {

    public static void main(String[] args) throws Exception {
        testRunReturnsCorrectField();
        testRunThrowsNoSuchFieldException();
    }

    static void testRunReturnsCorrectField() throws Exception {
        AtomicReferenceFieldUpdater<Object, Object> updater = AtomicReferenceFieldUpdater.newUpdater(Object.class, Object.class, "fieldName");
        Field field = updater.run();
        if (field == null) {
            throw new AssertionError("Expected non-null Field");
        }
        if (!field.getName().equals("fieldName")) {
            throw new AssertionError("Field name mismatch: " + field.getName());
        }
    }

    static void testRunThrowsNoSuchFieldException() {
        try {
            AtomicReferenceFieldUpdater<Object, Object> updater = new AtomicReferenceFieldUpdater<Object, Object>() {
                @Override
                public Field run() throws NoSuchFieldException {
                    throw new NoSuchFieldException("fieldName");
                }
            };
            updater.run();
            throw new AssertionError("Expected NoSuchFieldException");
        } catch (NoSuchFieldException e) {
            if (!e.getMessage().equals("fieldName")) {
                throw new AssertionError("Exception message mismatch: " + e.getMessage());
            }
        }
    }
}
