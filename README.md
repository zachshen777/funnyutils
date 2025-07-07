# funnyutils

![FunnyUtils](https://img.shields.io/badge/FunnyUtils-v1.0.0-brightgreen)

## 项目简介
`funnyutils` 是一个基于 Spring Boot 构建的微服务项目，提供了各种在线服务和有趣的小工具。目前，该项目的一个主要功能是将图像转换为 ASCII 艺术，为用户带来新奇的体验。

## 主要特性
- **图像转 ASCII 艺术**：支持用户上传图片，并根据自定义参数将其转换为 ASCII 艺术字符。
- **限流机制**：使用 Redis 和 Lua 脚本实现了灵活的限流功能，可根据IP进行限流，防止恶意请求。
- **多配置支持**：集成了 Nacos 作为配置中心，方便管理和动态调整项目配置。
- **日志管理**：采用 Log4j2 作为日志框架，支持控制台输出和按日期分割的滚动文件输出，便于问题排查和日志分析。

## 技术栈
- **后端框架**：Spring Boot 2.7.17
- **配置中心**：Nacos（非必需）
- **数据库**：MariaDB（非必需）
- **缓存**：Redis
- **日志框架**：Log4j2
- **JSON 处理**：FastJSON
- **图像处理**：Thumbnailator

## 快速开始
### 1. 克隆项目
```bash
git clone https://github.com/zachshen777/funnyutils.git
cd funnyutils
```

## 配置 Nacos(非必需)
- 在 src/main/resources/bootstrap.yaml 中配置 Nacos 服务器地址和命名空间：
- 应用名称与 Nacos 中 Data ID 的前缀必须匹配

## 配置数据库和缓存
- 确保 MariaDB 和 Redis 服务已启动，并在 application.yaml 中配置相应的连接信息。

## 构建和运行项目
```bash
mvn clean package
java -jar target/funnyutils-0.0.1-SNAPSHOT.jar
```

## 访问服务
- 项目启动后，访问 http://localhost:8080 即可使用相关服务。(端口可自定义)

## 贡献
- 如果你对本项目感兴趣，可以通过以下方式进行贡献：
- 提交 Bug 报告或功能请求
- 提交代码合并请求