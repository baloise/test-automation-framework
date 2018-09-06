pipeline {
    agent {
        label 'common'
    }

    parameters {
        booleanParam(defaultValue: false, description: 'Flag whether a release should be created.', name: 'CREATE_RELEASE')
    }

    tools {
        jdk 'JDK_1_8'
    }

    options {
        skipStagesAfterUnstable()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '28'))
        timeout(time: 1, unit: 'HOURS')
    }

    triggers {
        // at least once a day
        cron('H H(0-7) * * *')
        // every sixty minutes
        pollSCM('H/5 * * * *')
    }

    stages {
        stage("SCM Checkout") {
            steps {
                deleteDir()
                checkout scm
            }
        }

        stage("Maven") {
            steps {
                script {
                    if (params.CREATE_RELEASE) {
                        release()
                    } else {
                        mavenbuild mavenArgs: "dependency:copy-dependencies"
                    }
                }
            }
        }
        
        stage("Nexus Lifecycle") {
            when {
                expression { return !params.CREATE_RELEASE }
            }

            steps {
                nexusPolicyEvaluation iqApplication: 'com.baloise.testing.framework.taf', 
                                  iqScanPatterns: [[scanPattern: '**/target/dependency/*.jar']], 
                                  iqStage: 'build'
            }
        }
    }
}
