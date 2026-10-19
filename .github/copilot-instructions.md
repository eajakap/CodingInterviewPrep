# Copilot instructions

## Project shape

- This is a Java 17 Maven repository of independent interview-preparation exercises, not a single application. Algorithm solutions live under `src/main/java/org/educative/cip/m2/`, grouped by problem-solving pattern (for example, `sliding_window` and `two_pointers`); most classes are standalone and include sample `main` methods.
- The `drills` packages contain separate domain exercises, including Citi device-lifecycle and Goldman expense-sharing problems. Some related package-private types are intentionally kept together in one source file.
- The Goldman JUnit Jupiter tests are under `src/test/java/drills/goldman/`. Many algorithm exercises do not have Maven tests; their problem statements and sample drivers are in their source files.
- The additional drills under `src/main/java/drills/` have their own guide in `src/main/java/drills/README.md`. The `selfcontained` and `junit4` directories contain paired versions of the same drills; `junit4` runs against the checked-in JUnit-compatible shim by default.

## Build and test

- Build and run the Maven test suite: `mvn test`
- Build the package: `mvn package`
- Run one test class: `mvn -Dtest=BillSplitterTest test`
- Run one test method: `mvn -Dtest=BillSplitterTest#testUnevenSplit test`
- Run the standalone drills from the repository root with Bash: `bash src/main/java/drills/run_selfcontained.sh` or `bash src/main/java/drills/run_junit.sh`. Pass a substring to select a drill, such as `bash src/main/java/drills/run_selfcontained.sh Drill03`.
- There is no configured lint command or lint plugin in the Maven build.

## Repository conventions

- Keep algorithm exercises independent and in the package corresponding to their topic. Match the existing class/method interface and the problem constraints described in the source; multiple solution variants may coexist in one class.
- For the interview drills, the prompt/header and tests define the exercise. Preserve the test code when fixing a seeded bug. When changing one of the paired `selfcontained`/`junit4` drills, keep both copies' domain logic and test cases in sync.
- The drill runner scripts are Bash scripts and accept a filename substring to run a single drill; use the README in the drills directory for the full run options and drill-specific guidance.
