pipeline {
    agent {
        docker {
            image 'maven:3.9-amazoncorretto-21'
            args '--ipc=host -u root -v maven-repo:/root/.m2 -v playwright-browsers:/root/.cache/ms-playwright'
        }
    }

    parameters {
        string(
            name: 'TEST_FILTER',
            defaultValue: '',
            description: 'Surefire -Dtest filter: ElementsTest, ElementsTest#verifyYesRadioButtonSelectionTest, ElementsTest+WebTablesTest (empty = all)'
        )
        choice(
            name: 'BROWSER',
            choices: ['chromium', 'firefox', 'webkit'],
            description: 'Browser for UI tests'
        )
        string(
            name: 'PARALLEL_COUNT',
            defaultValue: '2',
            description: 'Number of parallel test threads'
        )
    }

    options {
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
    }

    stages {
        stage('Install Playwright browsers') {
            steps {
                sh "mvn -q exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.classpathScope=test -Dexec.args=\"install ${params.BROWSER}\""
                sh "mvn -q exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.classpathScope=test -Dexec.args=\"install-deps ${params.BROWSER}\""
            }
        }

        stage('Tests') {
            steps {
                script {
                    String parallelArg = "-Djunit.jupiter.execution.parallel.config.fixed.parallelism=${params.PARALLEL_COUNT}"
                    String browserArg  = "-Dbrowser=${params.BROWSER}"

                    if (params.TEST_FILTER) {
                        catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                            sh "mvn test -Dtest=${params.TEST_FILTER} -Dheadless=true ${browserArg} -Dtimeout=30000 ${parallelArg}"
                        }
                    } else {
                        parallel(
                            'API tests': {
                                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                                    sh "mvn test -Dgroups=api ${parallelArg}"
                                }
                            },
                            'UI tests': {
                                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                                    sh "mvn test -Dgroups=ui -Dheadless=true ${browserArg} -Dtimeout=30000 ${parallelArg}"
                                }
                            }
                        )
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
