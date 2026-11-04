/* @test    @summary Test getAgentProperties method of VMSupport class    @run main VMSupportWitupTest */

import java.util.Properties;
import jdk.internal.vm.VMSupport;

public class VMSupportWitupTest {

    public static void main(String[] args) {
        testGetAgentPropertiesReturnsProperties();
        testGetAgentPropertiesIsSingleton();
    }

    private static void testGetAgentPropertiesReturnsProperties() {
        Properties props = VMSupport.getAgentProperties();
        if (props == null) {
            throw new AssertionError("getAgentProperties() returned null");
        }
        if (!(props instanceof Properties)) {
            throw new AssertionError("getAgentProperties() did not return Properties instance");
        }
    }

    private static void testGetAgentPropertiesIsSingleton() {
        Properties firstCall = VMSupport.getAgentProperties();
        Properties secondCall = VMSupport.getAgentProperties();
        if (firstCall != secondCall) {
            throw new AssertionError("getAgentProperties() did not return singleton instance");
        }
    }
}
