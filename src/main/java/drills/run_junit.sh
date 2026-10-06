#!/usr/bin/env bash
# Run one JUnit-format drill, or all of them.
#   ./run_junit.sh                  -> runs every drill
#   ./run_junit.sh Drill03          -> runs the drills matching "Drill03"
#
# By default this compiles against junit-shim/, a minimal JUnit 4-compatible
# stand-in checked in beside the drills, so nothing needs downloading.
# To use the real thing instead, drop junit-4.13.2.jar and hamcrest-core-1.3.jar
# into lib/ and this script will pick them up.
set -u
cd "$(dirname "$0")"
out=".build"
mkdir -p "$out"

if [ -f lib/junit-4.13.2.jar ]; then
  cp="lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar"
  echo "(using real JUnit from lib/)"
else
  javac -d "$out/shim" $(find junit-shim -name '*.java') || exit 1
  cp="$out/shim"
fi

pattern="${1:-Drill0}"
for f in junit4/*"$pattern"*.java; do
  n="$(basename "$f" .java)"
  echo "=================================================================="
  echo "$n"
  echo "=================================================================="
  rm -rf "$out/$n" && mkdir -p "$out/$n"
  javac -d "$out/$n" -cp "$cp" "$f" || continue
  java -cp "$out/$n:$cp" "junit4.$n"
  echo
done
