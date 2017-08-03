FROM maven AS builder

ADD . /build

WORKDIR /build

RUN mvn dependency:resolve verify package

FROM tomcat:8-jre8

RUN rm -rf $CATALINA_HOME/webapps && mkdir $CATALINA_HOME/webapps && echo "America/Sao_Paulo" > /etc/timezone && dpkg-reconfigure -f noninteractive tzdata

COPY --from=builder /build/target/pdfcreator.war $CATALINA_HOME/webapps/pdfcreator.war
