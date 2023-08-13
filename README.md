# casa


## Run locally

(For hot reload)
```bash
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
