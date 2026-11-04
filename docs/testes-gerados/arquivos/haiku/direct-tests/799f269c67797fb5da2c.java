/*
 * @test
 * @summary Verifies ModuleReader.list() for exploded modules created via ModuleFinder, including closed-reader behavior
 * @modules java.compiler
 * @run main ModuleReferencesWitupTest
 */

import java.io.IOException;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class ModuleReferencesWitupTest {

    public static void main(String[] args) throws Exception {
        Path base = Files.createTempDirectory("mrwitup");
        Path src = base.resolve("src");
        Path out = base.resolve("mod");
        Files.createDirectories(src.resolve("p"));
        Files.createDirectories(out);

        Path moduleInfo = src.resolve("module-info.java");
        Path classA = src.resolve("p").resolve("A.java");
        Files.write(moduleInfo, "module m { exports p; }\n".getBytes(StandardCharsets.UTF_8));
        Files.write(classA, "package p; public class A { }\n".getBytes(StandardCharsets.UTF_8));

        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        check(javac != null, "system java compiler not available");
        int rc = javac.run(null, null, null, "-d", out.toString(),
                           moduleInfo.toString(), classA.toString());
        check(rc == 0, "compilation failed with rc=" + rc);

        Optional<ModuleReference> ref = ModuleFinder.of(out).find("m");
        check(ref.isPresent(), "module m not found in exploded directory");

        List<String> names;
        try (ModuleReader reader = ref.get().open()) {
            try (Stream<String> s = reader.list()) {
                names = s.collect(Collectors.toList());
            }
        }

        check(names.contains("module-info.class"), "missing module-info.class in " + names);
        check(names.contains("p/A.class"), "missing p/A.class in " + names);
        check(names.stream().noneMatch(String::isEmpty), "empty resource name returned: " + names);

        ModuleReader closed = ref.get().open();
        closed.close();
        boolean threw = false;
        try {
            closed.list();
        } catch (IOException expected) {
            threw = true;
        }
        check(threw, "list() on closed ModuleReader did not throw IOException");

        System.out.println("PASSED");
    }

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
