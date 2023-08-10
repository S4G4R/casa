# syntax=docker/dockerfile:1

FROM node:20 as build

WORKDIR /usr/src/app

COPY package.json .
COPY package-lock.json .

RUN npm install
RUN npm ci

FROM clojure:lein

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Clean cljs
RUN rm -rf resources/public/js

COPY profiles.clj.example profiles.clj

COPY shadow-cljs.edn shadow-cljs.edn

# Download deps (for caching)
COPY project.clj project.clj
RUN lein deps

COPY package.json .
COPY package-lock.json .

COPY --from=build /usr/src/app/node_modules ./node_modules

# Copy rest of the files
COPY src src
COPY resources resources

# Build JAR
RUN mv "$(lein uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p')" app-standalone.jar

# Expose port
EXPOSE 8080

ARG HYPERFIDDLE_ELECTRIC_APP_VERSION
ENV JAVA_OPTS="-DHYPERFIDDLE_ELECTRIC_SERVER_VERSION=$HYPERFIDDLE_ELECTRIC_APP_VERSION"

# RUN echo $HYPERFIDDLE_ELECTRIC_APP_VERSION > /tmp/foo
# CMD cat /tmp/foo

ENTRYPOINT exec java $JAVA_OPTS -jar app-standalone.jar

# REFERENCE
# https://github.com/hyperfiddle/electric-starter-app/blob/main/README.md#deployment
# https://github.com/hyperfiddle/electric-starter-app/blob/main/src-build/build.clj
# TODO
# App throws "Missing client program manifest". 
# Resources are missing from JAR? Try different strategy
# Create alias to build JS module first and then build JAR
