[private]
default:
    @just --list

# launch a jshell repl
repl:
    @rlwrap ./gradlew --console plain jshell

# build and run tests
test:
    @./gradlew build

# publish locally
publish-local:
    #ORG_GRADLE_PROJECT_signingKey=...
    #ORG_GRADLE_PROJECT_signingPassword=..
    @./gradlew publishToMavenLocal

# publish to maven central
publish:
    #https://oss.sonatype.org/
    #ORG_GRADLE_PROJECT_signingKey=...
    #ORG_GRADLE_PROJECT_signingPassword=..
    #OSSRH_USER=
    #OSSRH_PASSWORD=
    @./gradlew publishToSonatype closeAndReleaseSonatypeStagingRepository

# pull the latest specs
pull-specs:
    @curl -s https://raw.githubusercontent.com/jetify-com/typeid/main/spec/invalid.yml \
        > src/test/resources/invalid.yml
    @curl -s https://raw.githubusercontent.com/jetify-com/typeid/main/spec/valid.yml \
        > src/test/resources/valid.yml
