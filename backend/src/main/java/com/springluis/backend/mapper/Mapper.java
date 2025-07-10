package com.springluis.backend.mapper;

public interface Mapper <S, T> {
    T toTarget(S source);
    S toSource(T target);
}
