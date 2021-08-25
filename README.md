### 一、技术栈
#### 1.前端
- JS框架：JQuery
- CSS框架：[Semantic UI官网](https://semantic-ui.com/)
- Markdown编辑器：[编辑器 Markdown](https://pandao.github.io/editor.md/)
- 代码高亮：[代码高亮 prism](https://github.com/PrismJS/prism)
- 文章目录：[目录生成 Tocbot](https://tscanlin.github.io/tocbot/)
- 音乐盒[：zplayer](https://gitee.com/supperzh/zplayer)
- 照片墙[：lightbox插件](https://github.com/JavaScript-Kit/jkresponsivegallery)

#### 2.后端
- 核心框架：SpringBoot 2.2.5
- 项目构建：jdk1.8、Maven 3
- 持久层框架：Mybatis
- 模板框架：Thymeleaf
- 分布式缓存数据库：Redis
- 分布式搜索引擎：Elastic Search
- 负载均衡：Nginx
- 服务熔断、服务降级：Spring Cloud Hystrix
- 分页插件：PageHelper
- 加密：MD5加密
- 运行环境：阿里云服务器

#### 3.数据库
- MySQL 5.7

### 二、功能需求
因为是个人博客，所以没有做用户权限管理，只是简单的区分了一下普通用户和管理员用户

#### 1.普通用户
- 查看文章信息：文章列表、推荐文章、文章标题、文章内容、发布时间、访问量以及评论等信息
- 查看分类文章：分类列表、分类文章信息
- 查看时间轴：按照文章时间发布顺序查看文章
- 搜索文章：导航栏右边搜索框根据关键字搜索
- 听音乐：上一曲、下一曲、音量控制、播放顺序控制、查看歌词等
- 留言：留言并回复
- 查看相册信息：相册列表、照片名称、照片拍摄地点、时间、照片描述

#### 2.管理员用户（栈主）
- 拥有普通用户所有功能权限
- 登录：在主页路径下加“/admin”，可进入登录页面，根据数据库的用户名和密码进行登录
- 文章管理：查询文章列表、新增文章、编辑文章、删除文章、搜索文章
- 分类管理：查询分类列表、新增分类、编辑分类、删除分类
- 相册管理：查询相册列表、新增照片、编辑照片、删除照片

### 三、数据库设计

#### 1.数据表
- 博客数据表：t_blog
- 分类数据表：t_type
- 用户数据表：t_user
- 评论数据表：t_comment
- 留言数据表：t_message
- 相册数据表：t_picture

#### 2.实体关系

![image](https://note.youdao.com/yws/api/personal/file/CACBEF653E6745BF834B892952BFA816?method=download&shareKey=946ce6c8c2a0c84b107053cc254088f2)

- 博客和分类是多对一的关系：一个博客对应一个分类，一个分类可以对应多个博客
- 博客和用户是多对一的关系：一个博客对应一个用户，一个用户可以对应多个博客
- 博客和评论是一对多的关系：一个博客可以对应多个评论，一个评论对应一个博客
- 评论和回复是一对多的关系：一个评论可以对应多个回复，一个回复对应一个评论

> 留言和评论是一样的，还有友链和相册数据表和其他表没有关联

#### 3.实体属性
博客属性：

![image](https://note.youdao.com/yws/api/personal/file/CF5106DE22344BB481B3412A5BFAAAB9?method=download&shareKey=ae43526cd18465a53011fccbbe6e25c8)

分类属性：

![image](https://note.youdao.com/yws/api/personal/file/6C78F81369EB477B98D540266E5F4F58?method=download&shareKey=a820cfe6f3d6ebd860f0dff1b3bf8ba8)

用户属性：

![image](https://note.youdao.com/yws/api/personal/file/74338146706B4DB18C392C58C6287257?method=download&shareKey=2ad9cd5685461de2d57b983e85d97612)

评论属性：

![image](https://note.youdao.com/yws/api/personal/file/012FA484A82F47F5834BDB3FF7F1F668?method=download&shareKey=e971a831c76926da204a87ec5ac41a48)

留言属性：

![image](https://note.youdao.com/yws/api/personal/file/DC140E28066C4A54BAF8CDD351B4E479?method=download&shareKey=a39cdf3a8661671ac1a5de0b27a3e855)

相册属性：

![image](https://note.youdao.com/yws/api/personal/file/0648E5B2CA874903919E01F8854A36E4?method=download&shareKey=ef3e79e99d83615b6020eb8e9356c8c2)


- 博客属性：标题、内容、首图、标记、浏览次数、赞赏开启、版权开启、评论开启、是否发布、创建时间、更新时间、描述
- 分类属性：分类名称
- 用户属性：昵称、用户名、密码、邮箱、类型、头像、创建时间、更新时间
- 评论属性：昵称、邮箱、头像、评论内容、创建时间、博客id、父评论id、管理员id
- 留言属性：昵称、邮箱、头像、留言内容、创建时间、父留言id、管理员id
- 相册属性：图片地址、图片描述、图片名称、拍摄时间地点
