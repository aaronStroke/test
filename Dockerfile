FROM maven:3.9.6-amazoncorretto-17 AS build-stage

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN mvn package -Dmaven.test.skip=true

RUN mkdir -p /app && cp ./target/*.*ar /app/

FROM amazoncorretto:17-alpine

LABEL COMPANY="Farmacia LOAL"
LABEL MAINTAINER="aaron.lopalv@gmail.com"
LABEL APPLICATION="Pharmacy admin API"

RUN mkdir /usr/pharmacy-loal-app
RUN mkdir /usr/pharmacy-loal-app/reports
RUN mkdir /usr/pharmacy-loal-app/reports/subreports
RUN mkdir /usr/pharmacy-loal-app/reports/images

WORKDIR /usr/pharmacy-loal-app

COPY reports /usr/pharmacy-loal-app/reports

COPY --chown=1002:0 --from=build-stage /app/pharmacy-admin-api-1.0.0.jar /usr/pharmacy-loal-app/pharmacy-admin-api-1.0.0.jar

EXPOSE 10101

RUN apk update
RUN apk add --no-cache tzdata freetype fontconfig msttcorefonts-installer ttf-dejavu

RUN cp /usr/share/zoneinfo/America/Mexico_City /etc/localtime

ENV TZ=America/Mexico_City

CMD ["java", "-jar", "/usr/pharmacy-loal-app/pharmacy-admin-api-1.0.0.jar"]

VOLUME /logs
