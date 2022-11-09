package com.model.User;

public class History {
    private Statistics stats;

    public History(Statistics stats) {
        this.stats = stats;
    }

    public Statistics getStatistics() {
        return stats;
    }

    public Statistics getStats() {
        return stats;
    }

    @Override
    public String toString() {
        return stats.toString();
    }
}
