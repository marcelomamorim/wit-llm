/* @test    @summary Test for jdk.internal.module.ModulePatcher.getURL() method.
 * @run main ModulePatcherWitupTest
 */

import java.net.URL;
import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        testGetURLReturnsExpected();
    }

    public static void testGetURLReturnsExpected() {
        ModulePatcher patcher = new ModulePatcher(Collections.emptyMap());
        try {
            URL url = patcher.getURL();
            if (url != null) {
                System.out.println("Test passed: getURL() returned: " + url);
            } else {
                System.out.println("Test failed: getURL() returned null");
            }
        } catch (Throwable t) {
            System.out.println("Test failed with exception: " + t);
        }
    }
}
