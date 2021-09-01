package com.taskagile.domain.common.mail;

import com.taskagile.domain.model.user.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class MessageVariable {

    private String key;
    private Object value;

    public MessageVariable(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public static MessageVariable from(String key, Object value) {
        return new MessageVariable(key, value);
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MessageVariable that = (MessageVariable) o;

        return new EqualsBuilder().append(key, that.key).append(value, that.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(key).append(value).toHashCode();
    }

    @Override
    public String toString() {
        return "MessageVariable{" +
            "key='" + key + '\'' +
            ", value=" + value +
            '}';
    }
}
