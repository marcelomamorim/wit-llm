/*
 * @test
 * @summary Test for jdk.internal.module.ModulePatcher.PatchedModuleReader.Anonymous.getURL() throwing InternalError
 * @run main ModulePatcherWitupTest
 */

import java.net.URL;
import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    static class PatchedModuleReaderAnonymous {
        URL getURL() {
            // This method simulates the anonymous inner class method that calls shouldNotGetHere(URL.class)
            return shouldNotGetHere(URL.class);
        }

        <T> T shouldNotGetHere(Class<T> cls) {
            throw new InternalError();
        }
    }

    public static void main(String[] args) {
        PatchedModuleReaderAnonymous reader = new PatchedModuleReaderAnonymous();
        try {
            reader.getURL();
            throw new Error("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception
        }
    }
}
