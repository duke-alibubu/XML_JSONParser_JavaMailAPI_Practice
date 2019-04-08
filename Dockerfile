FROM maven

ADD . /app

WORKDIR /app

RUN mvn install

CMD ["mvn", "exec:java", "-Dexec.mainClass=com.cz3003.pmoreport.MainFunction"]
