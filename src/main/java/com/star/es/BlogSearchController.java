package com.star.es;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.entity.Type;
import com.star.entity.User;
import com.star.service.TypeService;
import com.star.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @创建人 qianqian
 * @创建时间 2021/8/14
 * @描述
 */
@Controller
public class BlogSearchController {

    @Autowired
    private BlogSearchService blogSearchService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private UserService userService;

    @PostMapping("/search")
    public String search(@RequestParam String query,
                         @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         Model model) {
        PageHelper.startPage(pageNum, 10);
        List<BlogSearchEntity> searchBlogs = blogSearchService.findByTitleLike(query);
        for (BlogSearchEntity searchBlog : searchBlogs) {
            System.out.println(searchBlog);
            // 为searchBlog设置类型名
            long typeId = searchBlog.getTypeId();
            System.out.println(typeId);
            String name = typeService.getType(typeId).getName();
            searchBlog.setTypeName(name);

            // 为searchBlog设置头像和昵称
            long userId = searchBlog.getUserId();
            User user = userService.getUserById(userId);
            searchBlog.setAvatar(user.getAvatar());
            searchBlog.setNickname(user.getNickname());
        }
        PageInfo<BlogSearchEntity> pageInfo = new PageInfo<>(searchBlogs);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }



}
