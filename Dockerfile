FROM glassfish:latest

EXPOSE 8080 4848

COPY postgresql-9.1-901-1.jdbc4.jar /usr/local/glassfish4/glassfish/domains/domain1/lib/postgresql-9.1-901-1.jdbc4.jar
COPY HackerNews4.war /



RUN /usr/local/glassfish4/bin/asadmin start-domain domain1 && \
#	/usr/local/glassfish4/bin/asadmin --host localhost --port 4848 enable-secure-admin && \
#    /usr/local/glassfish4/bin/asadmin change-admin-password --domain_name domain1 && \
#    /usr/local/glassfish4/bin/asadmin enable-secure-admin --port 4848 && \
	/usr/local/glassfish4/bin/asadmin create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGSimpleDataSource --restype javax.sql.DataSource --property portNumber=5432:password=HeltIgennemPerfekt:user=MasterHenning:serverName=postsecurity.cfukjtikvena.us-west-2.rds.amazonaws.com:databaseName=postgres postgres_pool && \
	/usr/local/glassfish4/bin/asadmin create-jdbc-resource --connectionpoolid postgres_pool jdbc/postgresql && \
	/usr/local/glassfish4/bin/asadmin -u admin deploy /HackerNews4.war && \
	/usr/local/glassfish4/bin/asadmin restart-domain domain1

#CMD [ "asadmin", "start-domain", "-v" ]