package com.bb.cy.dao;

import androidx.room.ColumnInfo;

public class NameTupleBean {

    @ColumnInfo(name = "firstName")
    public String first_name;

    @ColumnInfo(name = "lastName")
    public String last_name;
}
