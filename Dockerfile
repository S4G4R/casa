FROM clojure:lein

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Copy only project.clj and download deps (for caching)
COPY project.clj /usr/src/app/
RUN lein deps

# Copy rest of the files
COPY . /usr/src/app/
RUN cp profiles.clj.example profiles.clj

# Build JAR
RUN mv "$(lein uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p')" app-standalone.jar

# Expose port
EXPOSE 8080

ARG VERSION
ENV VERSION=$VERSION

CMD ["java", "-DHYPERFIDDLE_ELECTRIC_SERVER_VERSION=$VERSION", "-jar", "app-standalone.jar"]

# REFERENCE
# https://github.com/hyperfiddle/electric-starter-app/blob/main/README.md#deployment
# https://github.com/hyperfiddle/electric-starter-app/blob/main/src-build/build.clj
