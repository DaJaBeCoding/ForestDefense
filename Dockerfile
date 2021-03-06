FROM adoptopenjdk:11-jdk-openj9 as build

COPY . /build
WORKDIR /build
RUN apt-get update && apt-get upgrade -y && apt-get install -y \
    pandoc unzip wget \
  && rm -rf /var/lib/apt/lists/*
RUN cd updater/ && ./build-docker.sh

#FROM adoptopenjdk:11-jdk-openj9
FROM amazoncorretto:15

EXPOSE 27756

RUN mkdir /app

COPY --from=build /build/updater/tmp/ForestDefense-app/. /app/
COPY --from=build /build/updater/web/. /web2/
COPY --from=build /build/updater/run-docker.sh /app

RUN ls /app && chmod +x /app/bin/ForestDefense && chmod +x /app/run-docker.sh

CMD ["/app/run-docker.sh"]
