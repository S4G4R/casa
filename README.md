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
&& JVM_OPTS="-DHYPERFIDDLE_ELECTRIC_APP_VERSION=$(git describe --tags --long --always --dirty)" lein build \
&& JVM_OPTS="-DHYPERFIDDLE_ELECTRIC_APP_VERSION=$(git describe --tags --long --always --dirty)" lein run
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
flyctl secrets set STORYBLOK_BASE_URL=https://api.storyblok.com/v2/cdn/stories --stage
flyctl secrets set STORYBLOK_TOKEN=xyz --stage
flyctl deploy --build-arg HYPERFIDDLE_ELECTRIC_APP_VERSION=$(git describe --tags --long --always --dirty) --ha=false
flyctl scale memory 512
```

### Suspend/Resume
```bash
# Suspend
flyctl scale count 0 -a casa --yes
# Resume
flyctl scale count 1 -a casa --yes
flyctl scale memory 512
```

### Delete app
```bash
flyctl apps destroy casa
```
