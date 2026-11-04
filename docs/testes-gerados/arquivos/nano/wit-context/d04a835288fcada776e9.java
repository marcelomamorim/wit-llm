/*
 * @test
 * @summary Test for getContentLength method in ModulePatcher
 * @run main ModulePatcherWitupTest
 */

import java.io.IOException;
import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        testGetContentLength();
    }

    public static void testGetContentLength() {
        ModulePatcher patcher = new ModulePatcher(Collections.emptyMap());
        try {
            int result = patcher.getContentLength();
            // Since the method calls shouldNotGetHere, it should throw InternalError
            System.out.println("Unexpected success: " + result);
        } catch (InternalError e) {
            System.out.println("Caught expected InternalError: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        } catch (Throwable t) {
            System.out.println("Caught unexpected exception: " + t);
        }
    }
}
