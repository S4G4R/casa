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

COPY --from=build /usr/src/app/node_modules ./node_modules

# Copy sources
COPY src src

ARG HYPERFIDDLE_ELECTRIC_APP_VERSION
ENV JVM_OPTS="-DHYPERFIDDLE_ELECTRIC_SERVER_VERSION=$HYPERFIDDLE_ELECTRIC_APP_VERSION"

# Build client artifact
RUN lein build

# Copy resources after build
COPY resources resources

# Build JAR
RUN mv "$(lein uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p')" app-standalone.jar

# Expose port
EXPOSE 8080

ENV JAVA_OPTS="-DHYPERFIDDLE_ELECTRIC_SERVER_VERSION=$HYPERFIDDLE_ELECTRIC_APP_VERSION"

ENTRYPOINT exec java $JAVA_OPTS -jar app-standalone.jar

# REFERENCE
# https://github.com/hyperfiddle/electric-starter-app/blob/main/README.md#deployment
# https://github.com/hyperfiddle/electric-starter-app/blob/main/src-build/build.clj
