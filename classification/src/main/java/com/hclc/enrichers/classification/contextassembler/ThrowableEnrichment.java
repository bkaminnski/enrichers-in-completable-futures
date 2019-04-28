package com.hclc.enrichers.classification.contextassembler;

public class ThrowableEnrichment implements Enrichment {
    private final String threadName;
    private final Throwable throwable;

    public ThrowableEnrichment(Throwable throwable) {
        this.threadName = Thread.currentThread().getName();
        this.throwable = throwable;
    }

    @Override
    public void applyTo(Context context) {
        context.addAssemblyErrors(new AssemblyError(threadName, throwable.getMessage() == null || throwable.getMessage().isBlank() ? throwable.toString() : throwable.getMessage()));
    }
}
