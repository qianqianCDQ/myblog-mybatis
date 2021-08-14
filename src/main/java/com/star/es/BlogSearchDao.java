package com.star.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @创建人 qianqian
 * @创建时间 2021/8/14
 * @描述
 */
@Repository
public interface BlogSearchDao extends ElasticsearchRepository<BlogSearchEntity, Long> {

    List<BlogSearchEntity> findByTitleLike(String searchFromTitle);
}
