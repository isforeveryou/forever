#maven镜像
FROM maven:3-jdk-8 as BUILDER
#待构建的代码
COPY ./${code pom directory} /usr/app
#打jar包
WORKDIR /usr/app/
RUN mvn package -DskipTests -f pom.xml -s settings.xml

# 安装supervisor工具
RUN sudo apt-get install -y supervisor
RUN sudo mkdir -p /var/log/supervisor

#jdk镜像
FROM openjdk:latest
RUN mkdir -p /data/
#复制jar包到指定目录
COPY --from=BUILDER /usr/app/target/*.jar  /opt/lego-app/${your jar name}.jar
COPY supervisord.conf /usr/bin/supervisord.conf

#设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" >/etc/timezon

#启动时运行文件
WORKDIR /usr/bin
CMD ["/usr/bin/supervisord"]
 
