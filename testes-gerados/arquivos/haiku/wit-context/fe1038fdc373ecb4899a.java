/*
 * @test
 * @summary Verify that CleanerImpl.setCleanerImplAccess throws InternalError when the access function is already set
 * @modules java.base/jdk.internal.ref
 * @run main CleanerImplWitupTest
 */

import java.lang.ref.Cleaner;
import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {
    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();
        if (cleaner == null) {
            throw new RuntimeException("Cleaner.create() returned null");
        }

        try {
            CleanerImpl.setCleanerImplAccess(c -> null);
            throw new RuntimeException("Expected InternalError was not thrown");
        } catch (InternalError e) {
            if (!"cleanerImplAccess".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected InternalError message: " + e.getMessage());
            }
        }

        try {
            CleanerImpl.setCleanerImplAccess(null);
            throw new RuntimeException("Expected InternalError was not thrown on null argument");
        } catch (InternalError e) {
            if (!"cleanerImplAccess".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected InternalError message: " + e.getMessage());
            }
        }
    }
}
