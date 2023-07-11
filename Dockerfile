FROM public.ecr.aws/amazoncorretto/amazoncorretto:17 AS builder
ARG JAR_FILE=build/libs/spurt.jar
COPY ${JAR_FILE} spurt.jar
ENTRYPOINT ["java","-jar","/spurt.jar"]