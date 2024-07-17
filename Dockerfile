FROM public.ecr.aws/amazoncorretto/amazoncorretto:17 AS builder
ARG JAR_FILE=build/libs/spurt.jar
COPY ${JAR_FILE} spurt.jar
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "/spurt.jar"]
