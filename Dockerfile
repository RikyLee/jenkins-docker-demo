FROM harbor.olavoice.com/library/oracle/serverjre:8

#构建参数
ARG JAR_FILE
ARG WORK_PATH="/usr/local"
# 环境变量
ENV JAVA_OPTS="" \
    JAR_FILE=${JAR_FILE}


#定义时区参数
ENV TZ=Asia/Shanghai

#设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone

COPY target/$JAR_FILE $WORK_PATH/

WORKDIR $WORK_PATH

ENTRYPOINT exec java $JAVA_OPTS -jar $JAR_FILE