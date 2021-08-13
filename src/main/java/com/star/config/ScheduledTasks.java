package com.star.config;

import com.star.entity.Blog;
import com.star.queryvo.ShowBlog;
import com.star.service.BlogService;
import com.star.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @创建人 qianqian
 * @创建时间 2021/8/13
 * @描述
 */
@Component
public class ScheduledTasks {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BlogService blogService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void syncSeeCount() {
        Set<String> keys = redisUtil.keys("blog*");
        for (String key : keys) {
            Long blogId = Long.parseLong(key.substring(4));
            ShowBlog blog = blogService.getBlogById(blogId);
            blog.setViews((Integer) redisUtil.get(key));
            blogService.updateBlog(blog);
        }
    }

}
