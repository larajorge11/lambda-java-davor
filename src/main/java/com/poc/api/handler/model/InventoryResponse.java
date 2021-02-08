package com.poc.api.handler.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class InventoryResponse {

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
