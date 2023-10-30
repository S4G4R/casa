# casa


## Run locally

(For hot reload)
```bash
JVM_OPTS="-DHYPERFIDDLE_ELECTRIC_SERVER_VERSION=$(git describe --tags --long --always --dirty)" lein build
lein repl
=> (start)
```
OR
```bash
rm -rf resources/public/js \
&& JVM_OPTS="-DHYPERFIDDLE_ELECTRIC_SERVER_VERSION=$(git describe --tags --long --always --dirty)" lein build \
&& JVM_OPTS="-DHYPERFIDDLE_ELECTRIC_SERVER_VERSION=$(git describe --tags --long --always --dirty)" lein run
```

## Build and run Docker Image

```bash
HYPERFIDDLE_ELECTRIC_APP_VERSION=`git describe --tags --long --always --dirty` docker compose up --build
```

## Deploy to Fly.io

### Create app (only first time)
```bash
flyctl launch
```

### Deploy
```bash
flyctl deploy --build-arg HYPERFIDDLE_ELECTRIC_APP_VERSION=$(git describe --tags --long --always --dirty)
flyctl scale memory 512
```
