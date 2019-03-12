## Gradle Dependency Resolution
### Normal Gradle behavior
The default behavior of Gradle to pick the newest version also applies if a lower version has been declared locally, but another dependency transitively pulls in a newer version. This is in contrast with Maven, where a locally declared version [will always win](http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#Transitive_Dependencies).

For example, if your `build.gradle` specifies the dependency `org.springframework:spring-tx:3.2.3.RELEASE`, and another dependency declares `4.0.5.RELEASE` as a transitive dependency, then `4.0.5.RELEASE` will take precedence:

    dependencies {
        compile("org.springframework.data:spring-data-hadoop:2.0.0.RELEASE")
        compile("org.springframework:spring-tx:3.2.3.RELEASE")
        // will select org.springframework:spring-tx:4.0.5.RELEASE
    }

### Forcing specific versions
#### Using [`ResolutionStrategy.force(Object...)`](http://www.gradle.org/docs/current/dsl/org.gradle.api.artifacts.ResolutionStrategy.html#org.gradle.api.artifacts.ResolutionStrategy:force(java.lang.Object...)):

One option to force Gradle to use the specified version, thus obtaining a behavior similar to Maven, is to use `ResolutionStrategy.force(Object...)`:

    configurations.all {
        resolutionStrategy {
            force "org.springframework:spring-tx:3.2.3.RELEASE"
        }
    }

When there are multiple conflicting `ResolutionStrategy.force` calls, the last one to be executed wins:

    configurations.all {
        resolutionStrategy {
            force "org.springframework:spring-tx:3.2.5.RELEASE"
            force "org.springframework:spring-tx:3.2.7.RELEASE"
            force "org.springframework:spring-tx:3.2.6.RELEASE"
            // will select org.springframework:spring-tx:3.2.6.RELEASE (the last one)
        }
    }

Interestingly, `force` is implemented as a conflict resolution strategy, too (see [`ModuleForcingResolveRule`](https://github.com/gradle/gradle/blob/RB_1.12/subprojects/core-impl/src/main/groovy/org/gradle/api/internal/artifacts/ivyservice/resolutionstrategy/ModuleForcingResolveRule.java) and [`DefaultResolutionStrategy`](https://github.com/gradle/gradle/blob/RB_1.12/subprojects/core-impl/src/main/groovy/org/gradle/api/internal/artifacts/ivyservice/resolutionstrategy/DefaultResolutionStrategy.java#L85)). It is executed before the other resolution strategies.

#### Using [`ExternalDependency.force`](http://www.gradle.org/docs/current/javadoc/org/gradle/api/artifacts/ExternalDependency.html#isForce%28%29):

The other option is to use [`force`](http://www.gradle.org/docs/current/dsl/org.gradle.api.artifacts.dsl.DependencyHandler.html) on the dependency itself:

    dependencies {
        compile("org.springframework.data:spring-data-hadoop:2.0.0.RELEASE")
        compile("org.springframework:spring-tx:3.2.3.RELEASE") {
            force = true
        }
    }
    
This is perhaps the option that is most similar to Maven.

Note that if both `ResolutionStrategy.force` and `ExternalDependency.force` are used, the former takes precedence over the latter.