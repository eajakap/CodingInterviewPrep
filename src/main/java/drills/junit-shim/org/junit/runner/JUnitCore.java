package org.junit.runner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.notification.Failure;

/**
 * Stand-in for org.junit.runner.JUnitCore. Runs every public @Test method of each class,
 * in declaration order, on a fresh instance. Enough to drive the drills; it does not
 * implement @Before/@After, runners, or parallelism.
 */
public class JUnitCore {

    public static Result runClasses(Class<?>... classes) {
        Result result = new Result();
        for (Class<?> testClass : classes) {
            List<Method> tests = new ArrayList<>();
            for (Method method : testClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Test.class)) {
                    tests.add(method);
                }
            }
            for (Method test : tests) {
                result.recordRun();
                String header = test.getName() + "(" + testClass.getName() + ")";
                try {
                    Object instance = testClass.getDeclaredConstructor().newInstance();
                    test.setAccessible(true);
                    test.invoke(instance);
                } catch (InvocationTargetException e) {
                    result.recordFailure(new Failure(header, e.getCause()));
                } catch (Exception e) {
                    result.recordFailure(new Failure(header, e));
                }
            }
        }
        return result;
    }
}
