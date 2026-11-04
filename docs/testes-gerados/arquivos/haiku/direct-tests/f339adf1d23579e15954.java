/*
 * @test
 * @summary Basic observable behavior of ModuleDescriptor.Builder.exports(String)
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testExportsUnqualifiedPackage();
        testExportsDoesNotAffectOtherPackages();
        testExportsReturnsBuilderForChaining();
    }

    static void testExportsUnqualifiedPackage() {
        ModuleDescriptor md = ModuleDescriptor.newModule("m1")
                .exports("p1")
                .build();
        Set<ModuleDescriptor.Exports> exports = md.exports();
        if (exports.size() != 1) {
            throw new RuntimeException("Expected 1 export but found " + exports.size());
        }
        ModuleDescriptor.Exports e = exports.iterator().next();
        if (!e.source().equals("p1")) {
            throw new RuntimeException("Unexpected source: " + e.source());
        }
        if (e.isQualified()) {
            throw new RuntimeException("Export via exports(String) must not be qualified");
        }
    }

    static void testExportsDoesNotAffectOtherPackages() {
        ModuleDescriptor md = ModuleDescriptor.newModule("m2")
                .exports("p2")
                .build();
        for (ModuleDescriptor.Exports e : md.exports()) {
            if (e.source().equals("other")) {
                throw new RuntimeException("Unexpected export of 'other'");
            }
        }
        if (!md.exports().stream().anyMatch(e -> e.source().equals("p2"))) {
            throw new RuntimeException("Package p2 not exported");
        }
    }

    static void testExportsReturnsBuilderForChaining() {
        ModuleDescriptor md = ModuleDescriptor.newModule("m3")
                .exports("a")
                .exports("b")
                .build();
        if (md.exports().size() != 2) {
            throw new RuntimeException("Expected 2 exports but found " + md.exports().size());
        }
    }
}
