pipeline {
    agent {
        docker {
            image 'maven:3.9-eclipse-temurin-21'
            // root so Playwright can install Chromium system dependencies via apt-get
            // Named volumes persist Maven deps and browser binaries across runs
            args '--ipc=host -u root -v maven-repo:/root/.m2 -v playwright-browsers:/root/.cache/ms-playwright'
        }
    }

    options {
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
    }

    stages {
        stage('Install Playwright browsers') {
            steps {
                // Downloads Chromium + installs all required system libs (runs apt-get internally)
                // No-op on subsequent runs when the browser version is already cached
                sh 'mvn -q exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.classpathScope=test -Dexec.args="install --with-deps chromium"'
            }
        }

        stage('API tests') {
            steps {
                sh 'mvn test -Dgroups=api'
            }
        }

        stage('UI tests') {
            steps {
                sh 'mvn test -Dgroups=ui -Dheadless=true'
            }
        }

        stage('Reports') {
            steps {
                junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
                archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
            }
        }
    }

    post {
        failure {
            echo 'Pipeline failed — check the archived allure-results for screenshots.'
        }
    }
}
