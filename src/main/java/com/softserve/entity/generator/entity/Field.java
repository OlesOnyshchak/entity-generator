package com.softserve.entity.generator.entity;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "FIELD")
public class Field {

    @Id
    private String name;
    private String columnName;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
