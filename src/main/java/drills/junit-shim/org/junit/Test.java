package org.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Stand-in for org.junit.Test, so the drills in junit/ run without downloading JUnit.
 * Signature-compatible with JUnit 4: delete junit-shim/ and put a real junit-4.13.2.jar
 * on the classpath and the drills compile and run unchanged.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
    Class<? extends Throwable> expected() default None.class;

    long timeout() default 0L;

    /** Placeholder for JUnit's Test.None. */
    final class None extends Throwable {
        private None() {
        }
    }
}
