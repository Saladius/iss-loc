pipeline {
    agent any
    environment {
        DOCKER_IMAGE = 'saladius/iss-loc'
        DOCKER_TAG = 'latest'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'nohup ./your_unix_command &'
                    } else {
                        // Windows specific command or an alternative approach
                        bat 'start /B your_windows_command'
                    }
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("$DOCKER_IMAGE:$DOCKER_TAG")
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                        docker.image("$DOCKER_IMAGE:$DOCKER_TAG").push()
                    }
                }
            }
        }
    }
}
