package com.star.es.impl;

import com.star.es.BlogSearchDao;
import com.star.es.BlogSearchEntity;
import com.star.es.BlogSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @创建人 qianqian
 * @创建时间 2021/8/14
 * @描述
 */
@Service
public class BlogSearchServiceImpl implements BlogSearchService {

    @Autowired
    private BlogSearchDao blogSearchDao;


    @Override
    public List<BlogSearchEntity> findByTitleLike(String searchFromTitle) {
        return blogSearchDao.findByTitleLike(searchFromTitle);
    }
}
