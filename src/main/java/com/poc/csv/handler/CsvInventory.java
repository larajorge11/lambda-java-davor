package com.poc.csv.handler;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.poc.csv.handler.model.Inventory;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CsvInventory {

    public static List<Inventory> readCsvFile(final BufferedReader reader) throws CsvException, IOException {

        CSVReader csvReader = new CSVReader(reader);

        return csvReader.readAll().stream()
                .map(mapInventory)
                .skip(1)
                .collect(Collectors.toList());

    }

    private static BiFunction<String, String, List<String>> mapDataPipe =
            (data, separator) -> Arrays.asList(data.split("["+separator+"]"))
                    .stream().map(String::trim)
                    .filter(next -> !next.isEmpty())
                    .skip(1)
                    .collect(Collectors.toList());

    private static Function<String, Boolean> mapBoolean = data -> StringUtils.isNotEmpty(data) ? Boolean.valueOf(data) : null;

    private static Function<String[], Inventory> mapInventory = data -> Inventory.builder()
            .dataPointId(data[0])
            .dataPointName(data[1])
            .dataPointStandardName(data[2])
            .approvedAbbreviations(data[3])
            .dataPointDefinition(data[4])
            .applicableDataUniverses(mapDataPipe.apply(data[5], "|"))
            .applicableDomicile(data[6])
            .dataType(data[7])
            .dataPointPossibleValues(mapDataPipe.apply(data[8], ";"))
            .isDerived(mapBoolean.apply(data[9]))
            .methodologyReference(data[10])
            .build();

}
