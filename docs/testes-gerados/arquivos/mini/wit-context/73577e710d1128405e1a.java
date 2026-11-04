/*
 * @test
 * @summary Test deterministic behavior of jdk.internal.vm.VMSupport.getAgentProperties()
 * @run main VMSupportWitupTest
 */

import java.util.Properties;

public class VMSupportWitupTest {
    public static void main(String[] args) {
        // Call getAgentProperties multiple times to verify singleton and non-null
        Properties p1 = jdk.internal.vm.VMSupport.getAgentProperties();
        if (p1 == null) {
            throw new RuntimeException("getAgentProperties() returned null");
        }
        Properties p2 = jdk.internal.vm.VMSupport.getAgentProperties();
        if (p2 == null) {
            throw new RuntimeException("getAgentProperties() returned null on second call");
        }
        if (p1 != p2) {
            throw new RuntimeException("getAgentProperties() did not return the same instance on repeated calls");
        }
        // The returned Properties should contain at least zero or more keys (no exception expected)
        // Just check that it behaves like a Properties object
        String testKey = "java.version";
        String val = p1.getProperty(testKey);
        // val may be null if property not set, so no assert on value
        // Just ensure no exception and Properties is usable
        if (p1.stringPropertyNames() == null) {
            throw new RuntimeException("stringPropertyNames() returned null");
        }
        System.out.println("Test passed: getAgentProperties() returns singleton non-null Properties instance.");
    }
}
