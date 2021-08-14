package com.star.es;

import java.util.List;

/**
 * @创建人 qianqian
 * @创建时间 2021/8/14
 * @描述
 */
public interface BlogSearchService {

    List<BlogSearchEntity> findByTitleLike(String searchFromTitle);
}
