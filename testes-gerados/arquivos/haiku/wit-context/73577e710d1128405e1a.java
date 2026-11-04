/*
 * @test
 * @summary Checks that VMSupport.getAgentProperties returns a non-null, cached Properties instance
 * @modules java.base/jdk.internal.vm
 * @run main VMSupportWitupTest
 */

import java.util.Properties;
import jdk.internal.vm.VMSupport;

public class VMSupportWitupTest {
    public static void main(String[] args) {
        Properties first = VMSupport.getAgentProperties();
        if (first == null) {
            throw new RuntimeException("getAgentProperties returned null");
        }
        Properties second = VMSupport.getAgentProperties();
        if (first != second) {
            throw new RuntimeException("expected the same cached Properties instance");
        }
        String key = "witup.test.key";
        first.setProperty(key, "value");
        String seen = VMSupport.getAgentProperties().getProperty(key);
        if (!"value".equals(seen)) {
            throw new RuntimeException("property set on returned instance not visible on next call: " + seen);
        }
        first.remove(key);
    }
}
