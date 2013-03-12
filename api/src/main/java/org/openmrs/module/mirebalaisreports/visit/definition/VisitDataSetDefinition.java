/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.mirebalaisreports.visit.definition;

import org.openmrs.module.reporting.common.TimeQualifier;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDataDefinition;
import org.openmrs.module.reporting.data.encounter.definition.PatientToEncounterDataDefinition;
import org.openmrs.module.reporting.data.encounter.definition.PersonToEncounterDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.dataset.column.definition.RowPerObjectColumnDefinition;
import org.openmrs.module.reporting.dataset.definition.RowPerObjectDataSetDefinition;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.ParameterizableUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class VisitDataSetDefinition extends RowPerObjectDataSetDefinition {

    public static final long serialVersionUID = 1L;

    //***** PROPERTIES *****

    @ConfigurationProperty
    private List<Mapped<? extends VisitQuery>> rowFilters;


    @Override
    public List<Class<? extends DataDefinition>> getSupportedDataDefinitionTypes() {
        List<Class<? extends DataDefinition>> l = new ArrayList<Class<? extends DataDefinition>>();
        l.add(VisitDataDefinition.class);
        l.add(PersonDataDefinition.class);
        l.add(PatientDataDefinition.class);
        return l;
    }

    /**
     * Adds a new Column Definition given the passed parameters
     */
    public void addColumn(String name, DataDefinition dataDefinition, String mappings,  DataConverter... converters) {
        if (dataDefinition instanceof VisitDataDefinition) {
            getColumnDefinitions().add(new RowPerObjectColumnDefinition(name, dataDefinition, mappings, converters));
        }
        else if (dataDefinition instanceof PatientDataDefinition) {
            EncounterDataDefinition edd = new PatientToEncounterDataDefinition((PatientDataDefinition) dataDefinition);
            getColumnDefinitions().add(new RowPerObjectColumnDefinition(name, edd, mappings, converters));
        }
        else if (dataDefinition instanceof PersonDataDefinition) {
            EncounterDataDefinition edd = new PersonToEncounterDataDefinition((PersonDataDefinition) dataDefinition);
            getColumnDefinitions().add(new RowPerObjectColumnDefinition(name, edd, mappings, converters));
        }
        else {
            throw new IllegalArgumentException("Unable to add data definition of type " + dataDefinition.getClass().getSimpleName());
        }
    }


    @Override
    public void addColumns(String name, RowPerObjectDataSetDefinition dataSetDefinition, String mappings,
                           TimeQualifier whichValues, Integer numberOfValues, DataConverter... converters) {

        // TODO Implement this
    }

    /**
     * Add a new row filter with the passed parameter mappings
     */
    public void addRowFilter(VisitQuery filter, String mappings) {
        getRowFilters().add(new Mapped<VisitQuery>(filter, ParameterizableUtil.createParameterMappings(mappings)));
    }

    //***** PROPERTY ACCESS *****

    /**
     * @return the rowFilters
     */
    public List<Mapped<? extends VisitQuery>> getRowFilters() {
        if (rowFilters == null) {
            rowFilters = new ArrayList<Mapped<? extends VisitQuery>>();
        }
        return rowFilters;
    }

    /**
     * @param rowFilters the rowFilters to set
     */
    public void setRowFilters(List<Mapped<? extends VisitQuery>> rowFilters) {
        this.rowFilters = rowFilters;
    }
}
