#!/bin/bash
# entrypoint.sh

# フラグファイルのパスを指定
FLAG_FILE="/app/main-service/build/jooq-codegen-completed"

# jOOQのコード生成がまだ行われていないか確認
if [ ! -f "$FLAG_FILE" ]; then
  echo "Generating jOOQ code for main-service..."
  ./gradlew :main-service:generateJooq

  echo "Creating flag file: $FLAG_FILE"
  touch "$FLAG_FILE"
else
  echo "jOOQ code generation for main-service is not needed."
fi

# main-service アプリケーションの起動
./gradlew :main-service:bootRun -x generateJooq
