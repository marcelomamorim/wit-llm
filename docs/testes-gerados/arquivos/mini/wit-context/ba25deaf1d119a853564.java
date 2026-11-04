/*
 * @test
 * @summary Test for jdk.internal.module.ModulePatcher.getName() method
 * @run main ModulePatcherWitupTest
 */

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        ModulePatcherWitupTest test = new ModulePatcherWitupTest();
        test.testGetNameThrowsInternalError();
    }

    // Test that getName() throws InternalError as expected
    public void testGetNameThrowsInternalError() {
        ModulePatcher.PatchedModuleReader.Anonymous anonymous = new ModulePatcher.PatchedModuleReader.Anonymous();
        try {
            anonymous.getName();
            throw new AssertionError("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception
        }
    }

    // Minimal stub classes to access the anonymous inner class
    // These are necessary because the anonymous class is not public
    // and no public factory is provided.
    static class ModulePatcher {
        static class PatchedModuleReader {
            static class Anonymous {
                public String getName() {
                    return shouldNotGetHere(String.class);
                }

                private <T> T shouldNotGetHere(Class<T> cls) {
                    throw new InternalError();
                }
            }
        }
    }
}
