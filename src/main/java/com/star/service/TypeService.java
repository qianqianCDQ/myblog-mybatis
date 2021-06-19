package com.star.service;

import com.star.entity.Type;
import com.star.queryvo.FirstPageBlog;

import java.util.List;


public interface TypeService {

    int saveType(Type type);

    Type getType(Long id);

    List<Type> getAllType();

    List<Type> getAllTypeAndBlog();

    Type getTypeByName(String name);

    int updateType(Type type);

    void deleteType(Long id);




}