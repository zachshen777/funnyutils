# 使用 OpenJDK 11 作为基础镜像
FROM openjdk:11-jre-slim

# 设置工作目录
WORKDIR /www/wwwroot/funnyutils

# 复制打包好的 JAR 文件
COPY target/funnyutils-0.0.1-SNAPSHOT.jar funnyutils.jar

# 暴露应用端口
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "funnyutils.jar"]