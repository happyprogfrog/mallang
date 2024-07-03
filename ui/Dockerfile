FROM jetty:9.4-jre11

COPY ./start.d/deploy.ini /var/lib/jetty/start.d/deploy.ini
COPY ./start.d/http.ini /var/lib/jetty/start.d/http.ini
COPY ./webapps /var/lib/jetty/webapps

EXPOSE 9090
ENV JETTY_BASE /var/lib/jetty
CMD ["java", "-jar", "/usr/local/jetty/start.jar"]