package com.simudyne.beans;

import com.simudyne.rest.ResultCollector;
import com.simudyne.rest.RiskMeasure;

import java.util.Objects;

public class RequestModelTask {
    private final RiskMeasure riskMeasure;
    private final double brandFactor;
    private final ResultCollector resultCollector;
    private int priority = 5;

    public RequestModelTask(RiskMeasure riskMeasure, double brandFactor, ResultCollector resultCollector) {
        this.riskMeasure = riskMeasure;
        this.brandFactor = brandFactor;
        this.resultCollector = resultCollector;
    }

    public RiskMeasure getRiskMeasure() {
        return riskMeasure;
    }

    public double getBrandFactor() {
        return brandFactor;
    }

    public ResultCollector getResultCollector() {
        return resultCollector;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestModelTask that = (RequestModelTask) o;
        return Double.compare(that.brandFactor, brandFactor) == 0 &&
                priority == that.priority &&
                Objects.equals(riskMeasure, that.riskMeasure) &&
                Objects.equals(resultCollector, that.resultCollector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(riskMeasure, brandFactor, resultCollector, priority);
    }

    @Override
    public String toString() {
        return "RequestModelTask{" +
                "riskMeasure=" + riskMeasure +
                ", brandFactor=" + brandFactor +
                ", resultCollector=" + resultCollector +
                ", priority=" + priority +
                '}';
    }

}
