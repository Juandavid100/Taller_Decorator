#!/usr/bin/env bash
# Compiles and runs Mopa-Mopa Studio without Maven (requires JDK 17+).
# Usage: ./run.sh            -> web app at http://localhost:8080
#        ./run.sh --console  -> console demo
set -e
cd "$(dirname "$0")"
rm -rf out
mkdir -p out
javac -encoding UTF-8 -d out $(find src/main/java -name "*.java")
cp -r src/main/resources/* out/
java -cp out com.mopamopa.studio.Main "$@"
