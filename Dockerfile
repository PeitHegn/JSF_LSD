FROM glassfish:latest


EXPOSE 8080 4848 80


COPY postgresql-9.1-901-1.jdbc4.jar /usr/local/glassfish4/glassfish/domains/domain1/lib/postgresql-9.1-901-1.jdbc4.jar
COPY ./target/HackerNews4-1.0-SNAPSHOT.war /
COPY start.sh /

CMD /bin/bash start.sh
