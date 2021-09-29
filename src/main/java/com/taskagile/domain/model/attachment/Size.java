package com.taskagile.domain.model.attachment;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class Size implements Serializable {

    private final int width;
    private final int height;

    @Override
    public String toString() {
        return String.format("%dx%d", width, height);
    }
}
