package com.nikolaev.JobSensei.converter;

import com.fasterxml.jackson.databind.node.ArrayNode;

public abstract class Converter {
    public ArrayNode convert(ArrayNode json) {
        ArrayNode result = getInfo(json);
        return result;
    }

    protected abstract ArrayNode getInfo(ArrayNode json);
}
