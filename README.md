软件使用说明书

1. 概述

电影多维评分与智能推荐系统是一款基于Spring Boot 3、Vue 3和MySQL的全栈影评平台。它利用情感词典对用户评论进行情感分析，结合基础评分、情感评分和评论数量归一化三维加权模型生成电影综合评分，并通过协同过滤算法提供个性化推荐。此说明书旨在帮助用户快速理解、安装、使用和维护本系统。

本说明书包含系统概述、环境需求、安装部署、快速开始、功能介绍、系统设置、故障排查及附录等内容，确保用户能够在各个角色（普通用户、管理员）下顺畅操作。

2. 环境需求

2.1 硬件要求

CPU：4 核及以上

内存：8GB 及以上

存储：SSD 50GB 及以上

网络：稳定的局域网或互联网连接

2.2 软件要求

操作系统：Windows 10 / 11，或任意 Linux 发行版

Java：JDK 21

Node.js：16.x 及以上

Maven：3.9.8 及以上

MySQL：5.7 及以上

前端依赖：npm 或 yarn

2.3 第三方库和插件

Spring Boot 3.x

Mybatis-Plus

PageHelper 分页插件

Vue 3 + Element Plus

Axios

3. 安装部署

以下步骤以 Linux 环境为例，Windows 环境在命令和路径上略有差别，请根据实际调整。

3.1 数据库初始化

登录 MySQL:

mysql -u root -p

执行创建脚本:

DROP DATABASE IF EXISTS film_comment_system;
CREATE DATABASE film_comment_system CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE film_comment_system;
-- 导入 SQL 建表脚本
SOURCE /path/to/sql/film_comment_system.sql;

确认数据表已创建：

SHOW TABLES;

3.2 后端部署

克隆项目仓库：

git clone https://github.com/xiaoluncode/film_comment_system.git

cd film-comment-system/springboot/src/main/resources/application.yml

修改配置文件application.yml：

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/film_comment_system?useUnicode=true&characterEncoding=utf-8
    username: root
    password: your_password

编译并运行：

mvn clean package
java -jar target/film-comment-system-0.0.1-SNAPSHOT.jar

后端启动完成后，默认监听http://localhost:8080。

3.3 前端部署

进入前端目录：

cd ../frontend

安装依赖并启动：

npm install
npm run dev

访问前端页面：http://localhost:8081（或控制台提示的地址）。

4. 快速开始

打开浏览器，访问http://localhost:8081。

普通用户：点击“注册”，填写用户名、密码等信息进行注册；注册成功后登录。

管理员：请联系系统管理员分配管理员账户后登录。

登录后即可查看首页电影排行榜，并可通过导航栏进入电影列表、推荐、个人中心等模块。

5. 功能介绍

5.1 系统登录与注册

注册：填写用户名、密码、确认密码和昵称，密码将使用 MD5+盐值加密存储。

登录：支持用户名和密码登录；失败三次后自动锁定帐号10分钟。

找回密码：通过邮箱验证码重置密码。

5.2 首页——电影排行榜

展示当前评分最高的前五名电影，金、银、铜前三名分别用金、银、铜色标识。

每部电影显示：封面、名称、简介、综合评分（半星图）、评论数量。

点击电影卡片可进入电影详情页。

5.3 电影列表与筛选

支持按分类、年份、评分区间筛选，同时可输入关键字搜索。

分页展示，默认每页10条，可自定义页码和页大小。

5.4 电影详情与评论

基本信息：电影海报、名称、导演、主演、分类、语言、上映日期等。

评论展示：分“短评”和“长评”两种视图，按时间倒序排列。

发表评论：输入评论内容，系统将即时调用情感分析服务并展示情感得分。

5.5 情感分析结果查看

在评论条目右侧显示情感分：0.0-0.4（消极）、0.5（中性）、0.6-1.0（积极）。

可点击“情感详情”查看匹配到的情感词及权重。

5.6 个性化推荐

系统根据用户历史评分和评论分类，通过协同过滤算法生成实时推荐。

推荐列表在首页侧栏显示，最多显示3部电影；点击可快速进入详情。

5.7 用户个人中心

个人信息：查看和修改昵称、头像、密码。

我的评论：分页展示用户提交的所有评论，支持编辑和删除。

我的推荐：查看推荐历史并可重新刷新推荐列表。

5.8 管理端功能

电影管理：增删改查电影信息及分类；支持批量导入电影数据。

影评管理：审核、删除不当评论，支持关键字过滤和批量操作。

用户管理：查看所有用户，重置用户密码，锁定/解锁帐号。

公告管理：发布、修改和删除系统公告，支持定时发布。

数据监控：实时查看系统访问量、请求成功率和慢查询日志。

6. 系统设置

6.1 配置文件说明

application.yml：后端系统配置，包括数据库、端口、日志级别、定时任务参数。

vue.config.js：前端开发和打包配置，包含代理、路径别名等。

6.2 日志管理

后端日志采用 Log4j2 记录，日志文件位于logs/目录，按日期滚动。

前端使用浏览器控制台打印调试信息，生产环境会关闭详细日志。

7. 故障排查

问题描述

可能原因

解决方案

后端无法启动

数据库连接失败

检查application.yml中数据库URL、用户名、密码是否正确；MySQL是否已启动。

前端访问白屏

依赖安装失败或端口冲突

删除node_modules后重新npm install；检查端口是否被占用或修改vue.config.js端口配置。

评论情感得分异常

情感词典未加载

重启后端服务，检查SentimentAnalyzer初始化日志；确认情感词典配置正确。

推荐列表为空

用户无评论或阈值过滤

新用户采用随机推荐；若老用户出现，检查comment表中是否有数据并重启推荐服务。

8. 附录

8.1 配置文件示例

# application.yml
server:
  port: 8080
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/film_comment_system?useUnicode=true&characterEncoding=utf-8
    username: root
    password: password
logging:
  level:
    root: INFO
    com.example: DEBUG

8.2 常见术语

术语

解释

基础评分

用户直接给出的1~5分评分

情感评分

基于评论文本分析后的0.0~1.0得分

归一化评论数

对数函数归一化后的评论数量权重

协同过滤

根据用户行为相似度生成推荐
