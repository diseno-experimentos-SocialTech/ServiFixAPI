pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'Maven 3.6.3' // Maven en Jenkins
        JAVA_HOME = tool 'JDK 11' // JDK en Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/diseno-experimentos-SocialTech/ServiFixAPI.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    def mvnHome = tool 'Maven 3.6.3'
                    bat "${mvnHome}\\bin\\mvn clean package"
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    def mvnHome = tool 'Maven 3.6.3'
                    bat "${mvnHome}\\bin\\mvn test"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    def mvnHome = tool 'Maven 3.6.3'
                    bat "${mvnHome}\\bin\\mvn deploy"
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
