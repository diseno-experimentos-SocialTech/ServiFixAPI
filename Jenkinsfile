pipeline {
    agent any
    tools {
        maven 'MAVEN_3_6_3'
        jdk 'JDK_1_21'
    }
    stages {
        stage('Compile Stage') {
            steps {
                withMaven(maven: 'MAVEN_3_6_3') {
                    bat 'mvn clean compile'
                }
            }
        }
        stage('Testing Stage') {
            steps {
                withMaven(maven: 'MAVEN_3_6_3') {
                    bat 'mvn test'
                }
            }
        }
        stage('Package Stage') {
            steps {
                withMaven(maven: 'MAVEN_3_6_3') {
                    bat 'mvn package'
                }
            }
        }
    }
}

