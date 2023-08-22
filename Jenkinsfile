pipeline {
    agent {
        label 'common'
    }

    tools {
        jdk 'JDK_11'
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
                    mavenbuild mavenArgs: "dependency:copy-dependencies"
                }
            }
        }
        
        stage("Nexus Lifecycle") {
            steps {
                nexusPolicyEvaluation iqApplication: 'com.baloise.testing.framework.taf', 
                                  iqScanPatterns: [[scanPattern: '**/target/dependency/*.jar']], 
                                  iqStage: 'build'
            }
        }
    }
}
