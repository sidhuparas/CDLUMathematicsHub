function copyEnvVarsToGradleProperties {
    GRADLE_PROPERTIES=$HOME"/.gradle/gradle.properties"
    export GRADLE_PROPERTIES
    echo "Gradle Properties should exist at $GRADLE_PROPERTIES"

    if [ ! -f "$GRADLE_PROPERTIES" ]; then
        echo "Gradle Properties does not exist"

        echo "Creating Gradle Properties file..."
        touch $GRADLE_PROPERTIES

        echo "Writing TEST_API_KEY to gradle.properties..."
        echo "CDLU_BANNER_AD=$CDLU_BANNER_AD" >> $GRADLE_PROPERTIES
        echo "CDLU_INTERSTITIAL_AD=$CDLU_INTERSTITIAL_AD" >> $GRADLE_PROPERTIES
    fi
}
