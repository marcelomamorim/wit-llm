/*
 * @test
 * @summary Basic observable behaviour of ModuleDescriptor.Builder.opens(String) on a strict module builder
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("m.test");

        ModuleDescriptor.Builder returned = builder.opens("p.one");
        if (returned != builder) {
            throw new RuntimeException("opens(String) must return the same builder");
        }

        builder.opens("p.two");

        ModuleDescriptor md = builder.build();
        Set<ModuleDescriptor.Opens> opens = md.opens();
        if (opens.size() != 2) {
            throw new RuntimeException("expected 2 opens, got " + opens.size());
        }

        boolean foundOne = false;
        boolean foundTwo = false;
        for (ModuleDescriptor.Opens o : opens) {
            if (o.source().equals("p.one")) {
                foundOne = true;
            } else if (o.source().equals("p.two")) {
                foundTwo = true;
            }
        }
        if (!foundOne || !foundTwo) {
            throw new RuntimeException("missing expected opened packages");
        }
    }
}
