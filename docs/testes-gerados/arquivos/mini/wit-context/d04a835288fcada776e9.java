/*
 * @test
 * @summary Test for jdk.internal.module.ModulePatcher.PatchedModuleReader.Anonymous.getContentLength() throwing InternalError
 * @run main ModulePatcherWitupTest
 */

import java.io.IOException;

public class ModulePatcherWitupTest {

    static class PatchedModuleReaderAnonymous {
        int getContentLength() throws IOException {
            return shouldNotGetHere(int.class);
        }

        <T> T shouldNotGetHere(Class<T> c) {
            throw new InternalError("should not get here");
        }
    }

    public static void main(String[] args) {
        PatchedModuleReaderAnonymous reader = new PatchedModuleReaderAnonymous();
        try {
            reader.getContentLength();
            throw new RuntimeException("Test failed: expected InternalError not thrown");
        } catch (InternalError e) {
            // expected
            if (!"should not get here".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected InternalError message", e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }
}
