# drills.citi.Device Fleet — debugging drills

Eight self-contained Java exercises in the same shape as the `drills.citi.Device.java` interview
problem: a small domain, a class under test, a test suite that is red, and exactly one
seeded bug. Each file's header comment states the domain and the task, the same way the
original did. No file tells you where the bug is.

Each drill exists twice, with identical domain code and identical test bodies:

| folder            | package         | what it is                                              |
|-------------------|-----------------|---------------------------------------------------------|
| `selfcontained/`  | `selfcontained` | one file, a `main()`, a 30-line assert harness. `java selfcontained/Drill01_….java` and go. |
| `junit4/`         | `junit4`        | the interview's exact format: code first, `public static class TestSuite` with `@Test` methods, `JUnitCore.runClasses(...)` in `main`. |
| `junit-shim/`     | `org.junit`     | a minimal JUnit 4-compatible stand-in so `junit4/` runs with nothing downloaded. Delete it and drop a real `junit-4.13.2.jar` into `lib/` and the drills compile unchanged. |

The two drill folders are separate packages on purpose: the eight class names are
identical in both, so they would collide in the default package. As shipped you can
compile the whole tree at once — `javac -d out $(find . -name '*.java')` — or open the
root in an IDE, and nothing conflicts.

## Running

```bash
./run_selfcontained.sh              # every drill
./run_selfcontained.sh Drill03      # just that one
java selfcontained/Drill03_HeartbeatMonitor.java    # equivalent, JDK 11+

./run_junit.sh                      # every drill, JUnit format
./run_junit.sh Drill05

# or by hand, from this directory:
javac -d out $(find . -name '*.java')
java -cp out selfcontained.Drill03_HeartbeatMonitor
java -cp out junit4.Drill05_AlertLog
```

Requires a JDK 11 or newer (developed on 21). Nothing else.

## The drills

| # | File | Domain | Focus | Difficulty |
|---|------|--------|-------|------------|
| 01 | `Drill01_FleetRegistry` | slot → device lookup | object identity, hash-based collections | warm-up |
| 02 | `Drill02_FailureBudget` | escalation thresholds | autoboxing, reference vs. value comparison | medium |
| 03 | `Drill03_HeartbeatMonitor` | staleness ranking | the `Comparator` contract, numeric width | hard |
| 04 | `Drill04_MaintenanceQueue` | scheduling by due day | sorted collections, ordering vs. equality | hard |
| 05 | `Drill05_AlertLog` | per-device alert history | aliasing, shared mutable state, `Map` defaults | hard |
| 06 | `Drill06_CapacityPlanner` | locations at capacity | streams and collectors, boxed-type equality | medium-hard |
| 07 | `Drill07_SuspensionPolicy` | per-type failure limits | inheritance, what is and is not polymorphic | medium |
| 08 | `Drill08_TelemetryCollector` | concurrent ingest | atomicity of compound operations | hard |

Every one of them is a bug that survives code review. None is a typo, an off-by-one, or
a missing null check. In each case the code reads as if it does the right thing, and in
each case there is at least one test that passes — which is the part that makes them
worth practising on.

## How to work them

1. Read the header. It is the spec; the tests encode it.
2. Run the tests **before** reading the implementation, and read the failure output
   carefully. Which tests fail, and which pass, is most of the information you need.
   In several of these the passing test is the bigger clue.
3. Form a hypothesis, then confirm it with a one-line experiment (a `println`, a
   `System.identityHashCode`, a shrunk input) before you change any code.
4. Fix the code under test. Do not change the tests.
5. Re-run. Then check `SOLUTIONS.md`.

Suggested budget: 10–15 minutes each. If you blow past 20 on one, read that drill's
entry in `SOLUTIONS.md` and move on — the value is in recognising the shape next time,
not in grinding.

`SOLUTIONS.md` has, for each drill: the symptom, the root cause, the fix, the general
lesson, and a note on what to say out loud if you hit this shape in an interview.
It also opens with an analysis of the original `drills.citi.Device.java` you were given.

**Spoiler discipline:** `SOLUTIONS.md` gives away all eight. Don't skim it first.
