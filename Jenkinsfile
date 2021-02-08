pipeline {
    agent any
    stages {
        stage("SCM") {
            steps{
                git branch: 'main',
                credentialsId: 'github_lara',
                url: 'https://github.com/larajorge11/lambda-java-davor.git'
            }
        }
    }
}