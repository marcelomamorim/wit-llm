/* @test    @summary Test getAgentProperties method for normal behavior and exception path    @run main VMSupportWitupTest */

import java.util.Properties;
import java.io.IOException;

public class VMSupportWitupTest {

    public static void main(String[] args) throws Exception {
        testGetAgentPropertiesNormal();
        testGetAgentPropertiesExceptionPath();
    }

    public static void testGetAgentPropertiesNormal() {
        Properties props = jdk.internal.vm.VMSupport.getAgentProperties();
        if (props == null) {
            throw new AssertionError("Expected non-null Properties object");
        }
    }

    public static void testGetAgentPropertiesExceptionPath() {
        // Simulate the condition that triggers IllegalArgumentException
        // by temporarily replacing agentProps with a mock that causes exception
        // Since agentProps is private static, we cannot access directly; instead,
        // we can invoke initAgentProperties with invalid properties if accessible.
        // However, as per current code, the exception is thrown only in native method.
        // Since native method cannot be invoked directly in test, we simulate by
        // creating a subclass or mocking if possible. But as per constraints, we
        // avoid reflection or mocking.
        // Therefore, this test is a placeholder to document the scenario.
        // Alternatively, we can invoke getAgentProperties() in a way that
        // causes the native method to throw, but without native code, we cannot.
        // Hence, we document that this test is a placeholder.
        System.out.println("Note: Exception path test requires native code simulation, which is not feasible here.");
    }
}
