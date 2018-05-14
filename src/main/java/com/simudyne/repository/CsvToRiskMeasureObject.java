package com.simudyne.repository;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.simudyne.rest.RiskMeasure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.List;

@Repository
public class CsvToRiskMeasureObject {

    Logger LOGGER = LoggerFactory.getLogger(CsvToRiskMeasureObject.class);

    @Value("classpath:Simudyne_Backend_Test.csv")
    private Resource res;

    /**
     * Read risk measure from the file
     * @return riskMeasure object
     */
    public List<RiskMeasure> constructRiskMeasureFromFile() {
        List<RiskMeasure> riskMeasuresList = null;
        try {
            CsvSchema schema = csvSchema();
            CsvMapper csvMapper = new CsvMapper();
            csvMapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);

            ObjectReader riskMeasures = csvMapper.readerFor(RiskMeasure.class).with(schema);
            InputStream fileStream = res.getInputStream();
            MappingIterator<RiskMeasure> mi = riskMeasures.readValues(fileStream);
            riskMeasuresList = mi.readAll();
        } catch (Exception e) {
            LOGGER.error("Exception", e);
            e.printStackTrace();
        }
        return riskMeasuresList;
    }

    private CsvSchema csvSchema() {
        return new CsvSchema.Builder()
                .addColumn("agentBreed", CsvSchema.ColumnType.STRING)
                .addColumn("policyId", CsvSchema.ColumnType.NUMBER)
                .addColumn("age", CsvSchema.ColumnType.NUMBER)
                .addColumn("socialGrade", CsvSchema.ColumnType.NUMBER)
                .addColumn("paymentAtPurchase", CsvSchema.ColumnType.NUMBER)
                .addColumn("attributeBrand", CsvSchema.ColumnType.NUMBER)
                .addColumn("attributePrice", CsvSchema.ColumnType.NUMBER)
                .addColumn("attributePromotions", CsvSchema.ColumnType.NUMBER)
                .addColumn("autoRenew", CsvSchema.ColumnType.NUMBER)
                .addColumn("inertiaForSwitch", CsvSchema.ColumnType.NUMBER)
                .build()
                .withHeader();
    }
}
