# Asteroids

The Asteroids game implemented in my [2D pixel engine](https://github.com/Larqqa/java-2d-engine)

This is a simple proof of concept and testing project for the 2D engine project.

You need to have the engine repo locally and point to it in the settings.gradle
```
rootProject.name = 'Asteroids'
include ':lrq_engine'
project(':lrq_engine').projectDir = file('../java-2d-engine')
```
And add it into the dependencies of build.gradle
```
dependencies {
    compile project(':lrq_engine')
}
```