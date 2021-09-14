package com.taskagile.domain.common.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public abstract class AbstractBaseId implements Serializable {

    private final long id;

    public AbstractBaseId(long id) {
        this.id = id;
    }

    public long value() {
        return id;
    }

    public boolean isValid() {
        return id > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AbstractBaseId that = (AbstractBaseId) o;

        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }
}
