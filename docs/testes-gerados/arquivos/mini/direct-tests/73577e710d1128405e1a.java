/*
 * @test
 * @summary Test jdk.internal.vm.VMSupport.getAgentProperties() for caching and non-null return
 * @run main VMSupportWitupTest
 */

import java.util.Properties;

public class VMSupportWitupTest {
    public static void main(String[] args) throws Exception {
        // Call getAgentProperties multiple times and verify the same instance is returned
        Properties firstCall = jdk.internal.vm.VMSupport.getAgentProperties();
        if (firstCall == null) {
            throw new RuntimeException("getAgentProperties() returned null");
        }
        Properties secondCall = jdk.internal.vm.VMSupport.getAgentProperties();
        if (secondCall == null) {
            throw new RuntimeException("getAgentProperties() returned null on second call");
        }
        if (firstCall != secondCall) {
            throw new RuntimeException("getAgentProperties() did not cache the Properties instance");
        }
        // Check that the returned Properties is not empty (best effort, since initAgentProperties is native)
        if (firstCall.isEmpty()) {
            // Not necessarily a failure, but warn by printing
            System.err.println("Warning: agent properties is empty, native initAgentProperties may not have set any properties");
        }
        System.out.println("Test passed: getAgentProperties() returns cached non-null Properties instance");
    }
}
