package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.constant;

public enum JobPriority {
    LOW(0), MID(500), HIGH(1000);

    private final int priority;

    JobPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
