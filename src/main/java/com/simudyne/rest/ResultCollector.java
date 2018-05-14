package com.simudyne.rest;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ResultCollector {
    private final String policyId;
    private final double brandFactor;
    private volatile Map<String, Double> riskResultsPerYear;
    private double finalResults;
    private final String breed;

    public ResultCollector(final String policyId, final String breed, double brandFactor) {
        this.policyId = policyId;
        this.breed = breed;
        this.brandFactor = brandFactor;
        this.riskResultsPerYear = new ConcurrentHashMap<>();
    }

    public String getPolicyId() {
        return policyId;
    }

    public double getBrandFactor() {
        return brandFactor;
    }

    public void setFinalResults(double finalResults) {
        this.finalResults = finalResults;
    }

    public String getBreed() {
        return breed;
    }

    public String getCombinedKey() {
        return policyId + breed + brandFactor;
    }

    public void buildWithRiskResultsPerYear(int year, double result) {
        Objects.nonNull(riskResultsPerYear);
        System.out.println("key:::"+"Y"+year+"--" +result);
        this.riskResultsPerYear.put(String.valueOf("Y"+year), result);
    }

    public Map<String, Double> getRiskResultsPerYear() {
        return riskResultsPerYear;
    }

    public void buildWithFinalResults(double finalResults){
        this.finalResults = finalResults;
    }

    public double getFinalResults() {
        for (Map.Entry<String, Double> entry : riskResultsPerYear.entrySet()) {
            finalResults = entry.getValue() + finalResults;
        }
        return finalResults;
    }

    @Override
    public String toString() {
        return "ResultCollector{" +
                "policyId=" + policyId +
                ", brandFactor=" + brandFactor +
                ", riskResultsPerYear=" + riskResultsPerYear +
                ", finalResults=" + finalResults +
                ", breed='" + breed + '\'' +
                '}';
    }
}
