package org.junit.runner.notification;

/** Stand-in for org.junit.runner.notification.Failure. */
public class Failure {
    private final String testHeader;
    private final Throwable thrownException;

    public Failure(String testHeader, Throwable thrownException) {
        this.testHeader = testHeader;
        this.thrownException = thrownException;
    }

    public String getTestHeader() {
        return testHeader;
    }

    public Throwable getException() {
        return thrownException;
    }

    public String getMessage() {
        return thrownException.getMessage();
    }

    public String getTrace() {
        java.io.StringWriter writer = new java.io.StringWriter();
        writer.write(testHeader + "\n");
        thrownException.printStackTrace(new java.io.PrintWriter(writer));
        return writer.toString();
    }

    @Override
    public String toString() {
        return testHeader + ": " + thrownException.getMessage();
    }
}
