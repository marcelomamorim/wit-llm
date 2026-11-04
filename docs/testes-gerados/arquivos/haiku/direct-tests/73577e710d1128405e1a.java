/*
 * @test
 * @summary Verifies that VMSupport.getAgentProperties() returns a non-null, cached Properties instance
 * @modules java.base/jdk.internal.vm
 * @run main VMSupportWitupTest
 */

import java.util.Properties;
import jdk.internal.vm.VMSupport;

public class VMSupportWitupTest {

    public static void main(String[] args) {
        Properties first = VMSupport.getAgentProperties();
        if (first == null) {
            throw new RuntimeException("getAgentProperties() returned null");
        }

        Properties second = VMSupport.getAgentProperties();
        if (first != second) {
            throw new RuntimeException("getAgentProperties() did not return the same cached instance");
        }

        String key = "witup.test.agent.key";
        String value = "witup-value";
        first.setProperty(key, value);
        String seen = VMSupport.getAgentProperties().getProperty(key);
        if (!value.equals(seen)) {
            throw new RuntimeException("Property set on returned object not visible on next call: " + seen);
        }
    }
}
