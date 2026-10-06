package org.junit.runner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runner.notification.Failure;

/** Stand-in for org.junit.runner.Result. */
public class Result {
    private final List<Failure> failures = new ArrayList<>();
    private int runCount = 0;

    void recordRun() {
        runCount++;
    }

    void recordFailure(Failure failure) {
        failures.add(failure);
    }

    public int getRunCount() {
        return runCount;
    }

    public int getFailureCount() {
        return failures.size();
    }

    public List<Failure> getFailures() {
        return Collections.unmodifiableList(failures);
    }

    public boolean wasSuccessful() {
        return failures.isEmpty();
    }
}
