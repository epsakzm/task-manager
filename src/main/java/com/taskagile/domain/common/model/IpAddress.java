package com.taskagile.domain.common.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class IpAddress implements Serializable {

    private String value;

    public IpAddress(String value) {
        this.value = value == null ? "" : value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "IpAddress{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        IpAddress ipAddress = (IpAddress) o;

        return new EqualsBuilder().append(value, ipAddress.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).toHashCode();
    }
}
