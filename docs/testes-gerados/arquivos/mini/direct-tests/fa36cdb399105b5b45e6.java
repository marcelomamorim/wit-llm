/*
 * @test
 * @summary Test sun.util.resources.LocaleData run() method
 * @run main LocaleDataWitupTest
 */

import sun.util.resources.LocaleData;
import sun.util.locale.provider.LocaleProviderAdapter;
import java.util.ResourceBundle;

public class LocaleDataWitupTest {
    public static void main(String[] args) {
        LocaleData ld = new LocaleData(LocaleProviderAdapter.Type.JRE);
        ResourceBundle rb = ld.run();
        if (rb == null) {
            throw new RuntimeException("run() returned null ResourceBundle");
        }
        // call run() multiple times to check determinism
        ResourceBundle rb2 = ld.run();
        if (rb2 == null) {
            throw new RuntimeException("run() returned null ResourceBundle on second call");
        }
        if (!rb.getClass().equals(rb2.getClass())) {
            throw new RuntimeException("run() returned different ResourceBundle classes on repeated calls");
        }
    }
}
