package org.example;

import java.math.BigDecimal;

public class Properties {
    private String id;
    private String size;
    private String NAME;
    private BigDecimal[] cp;
    private String childNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public BigDecimal[] getCp() {
        return cp;
    }

    public void setCp(BigDecimal[] cp) {
        this.cp = cp;
    }

    public String getChildNum() {
        return childNum;
    }

    public void setChildNum(String childNum) {
        this.childNum = childNum;
    }
}
