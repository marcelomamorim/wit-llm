/*
 * @test
 * @summary Second call to CleanerImpl.setCleanerImplAccess must throw InternalError with message "cleanerImplAccess"
 * @modules java.base/jdk.internal.ref
 * @run main CleanerImplWitupTest
 */

import java.lang.ref.Cleaner;
import java.util.function.Function;

import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {
    public static void main(String[] args) {
        Function<Cleaner, CleanerImpl> access = c -> null;

        try {
            CleanerImpl.setCleanerImplAccess(access);
        } catch (InternalError e) {
            // The access function may already be set by Cleaner static initialization.
        }

        try {
            CleanerImpl.setCleanerImplAccess(access);
            throw new RuntimeException("expected InternalError on second call");
        } catch (InternalError e) {
            if (!"cleanerImplAccess".equals(e.getMessage())) {
                throw new RuntimeException("wrong message: " + e.getMessage());
            }
        }
    }
}
