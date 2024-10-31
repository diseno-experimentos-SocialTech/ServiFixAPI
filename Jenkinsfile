pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'MAVEN_3_6_3' // Maven en Jenkins
        JAVA_HOME = tool 'JDK_1_21' // JDK en Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/diseno-experimentos-SocialTech/ServiFixAPI.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    def mvnHome = tool 'MAVEN_3_6_3'
                    bat "${mvnHome}\\bin\\mvn clean package"
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    def mvnHome = tool 'MAVEN_3_6_3'
                    bat "${mvnHome}\\bin\\mvn test"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    def mvnHome = tool 'MAVEN_3_6_3'
                    bat "${mvnHome}\\bin\\mvn deploy"
                }
            }
        }
    }

    post {
        always {
            node('windows') { // Usa la etiqueta de tu agente aquí
                archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
                junit '**/target/surefire-reports/*.xml'
            }
        }
    }
}
