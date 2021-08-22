package com.star.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.star.dao.BlogDao;
import com.star.entity.Comment;
import com.star.queryvo.DetailedBlog;
import com.star.queryvo.FirstPageBlog;
import com.star.queryvo.RecommendBlog;
import com.star.service.BlogService;
import com.star.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class IndexController {

    @Value("${server.port}")
    private String port;

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    // 分页查询博客列表
    @HystrixCommand(commandKey = "index",
                    fallbackMethod = "getFallback",
                    commandProperties = {
                        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
                        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
    })
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                        RedirectAttributes attributes){
        PageHelper.startPage(pageNum, 10);
        List<FirstPageBlog> allFirstPageBlog = blogService.getAllFirstPageBlog();
        List<RecommendBlog> recommendedBlog = blogService.getRecommendedBlog();
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(allFirstPageBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("recommendedBlogs", recommendedBlog);
        System.out.println("port is " + port);
        return "index";
    }

    /*
    // 搜索博客
    @PostMapping("/search")
    public String search(Model model,
                         @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         @RequestParam String query) {
        PageHelper.startPage(pageNum, 10);
        List<FirstPageBlog> searchBlog = blogService.getSearchBlog(query);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(searchBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }*/

    // 跳转博客详情页面
    @HystrixCommand(commandKey = "blog",
            fallbackMethod = "getFallbackBlog",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        DetailedBlog detailedBlog = blogService.getDetailedBlog(id);
        List<Comment> comments = commentService.listCommentByBlogId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("blog", detailedBlog);
        return "blog";
    }

    // 博客信息
    @GetMapping("/footer/blogmessage")
    public String blogMessage(Model model){
        int blogTotal = blogService.getBlogTotal();
        int blogViewTotal = blogService.getBlogViewTotal();
        int blogCommentTotal = blogService.getBlogCommentTotal();
        int blogMessageTotal = blogService.getBlogMessageTotal();

        model.addAttribute("blogTotal",blogTotal);
        model.addAttribute("blogViewTotal",blogViewTotal);
        model.addAttribute("blogCommentTotal",blogCommentTotal);
        model.addAttribute("blogMessageTotal",blogMessageTotal);
        return "index :: blogMessage";
    }

    @ResponseBody
    public String getFallback(Model model,
                              @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                              RedirectAttributes attributes) {
        return "服务超时，请稍后再试...";
    }

    @ResponseBody
    public String getFallbackBlog(@PathVariable Long id, Model model) {
        return "请稍微再点击进入...";
    }

}