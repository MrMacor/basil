# basil
![Java CI With Gradle](https://img.shields.io/github/workflow/status/MrMacor/basil/Java%20CI%20with%20Gradle/main?color=629660&label=basil) [![MIT License](https://img.shields.io/badge/license-MIT-blue)](license.txt)

`basil` is a library written in Java which aims to expand and improve on the [Guava](https://github.com/google/guava "google/guava")/[Caffeine](https://github.com/ben-manes/caffeine "ben-manes/caffeine") `Cache<K, V>` implementation.

# Building Requirements
As of now, you'll need to build `basil` yourself.

You'll need the following software:
  - Java 11 or higher
  - Gradle 7

# Building Steps
With IntelliJ IDEA:
  1. Click on `File` > `New` > `Project from Version Control...`, paste in `https://github.com/MrMacor/basil.git`, then click `Clone`.
  2. Open up the Terminal, and run `./gradlew build`.
  3. `basil-1.0.0` will now be installed in `./builds/libs`.
  4. You can now install the built jar to your local Maven repository by opening the `Run Anything` menu and pasting in:
     <br>`mvn install:install-file `
     <br>`-Dfile=./build/libs/basil-1.0.0.jar `
     <br>`-DgroupId=me.mrmacor.basil `
     <br>`-DartifactId=basil `
     <br>`-Dversion=1.0.0 `
     <br>`-Dpackaging=jar `

From the command line:
  1. Run `git clone https://github.com/MrMacor/basil.git`
  2. `cd` into the cloned repository.
  3. Run `./gradlew build`.
  4. `basil-1.0.0` will now be installed in `./builds/libs`.

# Contributing
Read [contributing.md](../main/contributing.md "contributing.md").

# License
`basil` is licensed under the [MIT License](../main/LICENSE "LICENSE").
