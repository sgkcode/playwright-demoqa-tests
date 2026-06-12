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
                sh 'mvn -q exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.classpathScope=test -Dexec.args="install chromium"'
                sh 'mvn -q exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.classpathScope=test -Dexec.args="install-deps chromium"'
                sh 'apt-get install -y --no-install-recommends libxcursor1 libgtk-3-0t64 libpangocairo-1.0-0 libcairo-gobject2 libgdk-pixbuf-2.0-0'
            }
        }

        stage('Tests') {
            parallel {
                stage('API tests') {
                    steps {
                        catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                            sh 'mvn test -Dgroups=api'
                        }
                    }
                }
                stage('UI tests') {
                    steps {
                        catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                            sh 'mvn test -Dgroups=ui -Dheadless=true -Dtimeout=30000'
                        }
                    }
                }
            }
        }

        stage('Reports') {
            steps {
                sh 'chmod -R a+w target/ || true'
                junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
                allure results: [[path: 'target/allure-results']]
            }
        }
    }

    post {
        failure {
            echo 'Pipeline failed — check the archived allure-results for screenshots.'
        }
    }
}
