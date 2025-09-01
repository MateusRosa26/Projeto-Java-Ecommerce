package com.ecommerce.business;

import java.util.List;

public interface BusinessHandler<T> {
    T create(T object);
    T update(T object, long id);
    boolean delete(long id);
    T find(long id);
    List<T> list();
    List<T> list(String field, String filter, boolean exact);
}
