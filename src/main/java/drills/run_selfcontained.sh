#!/usr/bin/env bash
# Run one drill, or all of them.
#   ./run_selfcontained.sh                     -> runs every drill
#   ./run_selfcontained.sh Drill03             -> runs the drills matching "Drill03"
set -u
cd "$(dirname "$0")"
pattern="${1:-Drill0}"
for f in selfcontained/*"$pattern"*.java; do
  echo "=================================================================="
  echo "$(basename "$f")"
  echo "=================================================================="
  java "$f"
  echo
done
