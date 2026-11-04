/*
 * @test
 * @summary Verify that ModuleDescriptor.Builder.opens(String) creates an unqualified opens directive and returns the same builder
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testReturnsSameBuilder();
        testUnqualifiedOpens();
        testMultiplePackages();
    }

    static void testReturnsSameBuilder() {
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m");
        ModuleDescriptor.Builder r = b.opens("p.q");
        if (r != b) {
            throw new RuntimeException("opens(pn) must return the same Builder");
        }
    }

    static void testUnqualifiedOpens() {
        ModuleDescriptor md = ModuleDescriptor.newModule("m")
                .opens("p.q")
                .build();
        if (md.opens().size() != 1) {
            throw new RuntimeException("expected 1 opens, got " + md.opens().size());
        }
        ModuleDescriptor.Opens o = md.opens().iterator().next();
        if (!o.source().equals("p.q")) {
            throw new RuntimeException("unexpected source: " + o.source());
        }
        if (!o.targets().isEmpty()) {
            throw new RuntimeException("opens(pn) must be unqualified, targets: " + o.targets());
        }
        if (md.isOpen()) {
            throw new RuntimeException("module must not be open");
        }
    }

    static void testMultiplePackages() {
        ModuleDescriptor md = ModuleDescriptor.newModule("m")
                .opens("p.one")
                .opens("p.two")
                .build();
        if (md.opens().size() != 2) {
            throw new RuntimeException("expected 2 opens, got " + md.opens().size());
        }
        for (ModuleDescriptor.Opens o : md.opens()) {
            if (!o.targets().isEmpty()) {
                throw new RuntimeException("opens must be unqualified: " + o.source());
            }
        }
    }
}
