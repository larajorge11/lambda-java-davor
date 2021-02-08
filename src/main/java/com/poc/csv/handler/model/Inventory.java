package com.poc.csv.handler.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Inventory {
    private String dataPointId;
    private String dataPointName;
    private String dataPointStandardName;
    private String approvedAbbreviations;
    private String dataPointDefinition;
    private List<String> applicableDataUniverses;
    private String applicableDomicile;
    private String dataType;
    private List<String> dataPointPossibleValues;
    private Boolean isDerived;
    private String methodologyReference;
}
