package com.star.config;

import com.star.entity.Blog;
import com.star.es.BlogSearchDao;
import com.star.es.BlogSearchEntity;
import com.star.es.BlogSearchService;
import com.star.queryvo.BlogQuery;
import com.star.queryvo.DetailedBlog;
import com.star.queryvo.ShowBlog;
import com.star.service.BlogService;
import com.star.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @创建人 qianqian
 * @创建时间 2021/8/13
 * @描述
 */
@Service
public class ScheduledTasks {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogSearchDao blogSearchDao;

    // 每天12点定时将Redis缓存中数据刷新到mysql数据库中
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

    // 每隔一个小时将mysql数据更新到Es
    @Scheduled(cron = "30 * * * * ?")
    public void addMysqlDataToEs() {
        deleteEsData(); //因为会有冗余数据存在，先全部清空，但是会造成在该时刻搜索不到数据的情况
        addDataToEs(); //批量复制数据
    }

    // 清空ES中的数据
    private void deleteEsData() {
        blogSearchDao.deleteAll();
    }

    // 批量复制数据
    private void addDataToEs() {
        List<BlogQuery> blogs = blogService.getAllBlog();
        List<BlogSearchEntity> blogSearchEntities = new ArrayList<>();
        for (BlogQuery blog : blogs) {
            // 先获得BlogQuery中的博客id
            long id = blog.getId();
            // 然后根据博客id查询出博客的详细信息
            DetailedBlog detailedBlog = blogService.getDetailedBlog(id);
            // 将博客详细信息赋值到searchEntity中
            BlogSearchEntity searchEntity = new BlogSearchEntity();
            searchEntity.setId(detailedBlog.getId());
            searchEntity.setTitle(detailedBlog.getTitle());
            searchEntity.setTypeId(detailedBlog.getTypeId());
            searchEntity.setUserId(detailedBlog.getUserId());
            searchEntity.setDescription(detailedBlog.getDescription());
            searchEntity.setAppreciation(detailedBlog.isAppreciation());
            searchEntity.setCommentabled(detailedBlog.isCommentabled());
            searchEntity.setUpdateTime(detailedBlog.getUpdateTime());
            searchEntity.setFirstPicture(detailedBlog.getFirstPicture());
            searchEntity.setFlag(detailedBlog.getFlag());
            searchEntity.setShareStatement(detailedBlog.isShareStatement());
            searchEntity.setTypeName(detailedBlog.getTypeName());
            searchEntity.setViews(detailedBlog.getViews());
            blogSearchEntities.add(searchEntity);
        }
        if (blogSearchEntities.size() > 0)
            System.out.println("********从Mysql向Es更新数据*********");
        for (BlogSearchEntity blogSearchEntity : blogSearchEntities) {
            blogSearchDao.index(blogSearchEntity);
        }
    }

}
