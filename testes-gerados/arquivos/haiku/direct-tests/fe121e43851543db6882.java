/*
 * @test
 * @summary Verifies Class.getDeclaredAnnotationsByType for direct, repeated, inherited and absent annotations, and NPE on null argument
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ClassWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(Tags.class)
    @interface Tag {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Tags {
        Tag[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @interface Marker {
    }

    @Tag("a")
    @Tag("b")
    @Marker
    static class Base {
    }

    static class Child extends Base {
    }

    @Tag("single")
    static class Single {
    }

    static class Empty {
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        // Repeated annotations are all returned in declaration order
        Tag[] tags = Base.class.getDeclaredAnnotationsByType(Tag.class);
        check(tags.length == 2, "expected 2 Tag annotations, got " + tags.length);
        check("a".equals(tags[0].value()), "first value should be a");
        check("b".equals(tags[1].value()), "second value should be b");

        // Single directly present annotation is returned as array of length 1
        Tag[] single = Single.class.getDeclaredAnnotationsByType(Tag.class);
        check(single.length == 1, "expected 1 Tag on Single, got " + single.length);
        check("single".equals(single[0].value()), "value should be single");

        // Directly present non-repeatable annotation
        Marker[] markers = Base.class.getDeclaredAnnotationsByType(Marker.class);
        check(markers.length == 1, "expected 1 Marker on Base, got " + markers.length);

        // Absent annotation yields empty array
        Tag[] none = Empty.class.getDeclaredAnnotationsByType(Tag.class);
        check(none != null && none.length == 0, "expected empty array for Empty");

        // @Inherited annotation is not a declared annotation of the subclass
        Marker[] inheritedDeclared = Child.class.getDeclaredAnnotationsByType(Marker.class);
        check(inheritedDeclared.length == 0,
              "Child should not declare Marker, got " + inheritedDeclared.length);

        // ...but the non-declared accessor does see the inherited annotation
        Marker[] inheritedAll = Child.class.getAnnotationsByType(Marker.class);
        check(inheritedAll.length == 1, "Child should inherit Marker, got " + inheritedAll.length);

        // Null argument must raise NullPointerException
        boolean npe = false;
        try {
            Base.class.getDeclaredAnnotationsByType(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "expected NullPointerException for null annotationClass");
    }
}
