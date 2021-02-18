pipeline {
    agent any
    environment {
        APP = ""
    }
    stages {
        stage('Build') {
            when {
                anyOf { branch 'develop'; branch 'master' }
            }
            steps {
                script {
                    APP = docker.build("npetersdev/spring-petclinic-rest")
                }
            }
        }
        stage('Run API test') {
            when {
                anyOf { branch 'develop'; branch 'master' }
            }
            steps {
                sh 'docker run --rm -d --network=host --name rest-test-container npetersdev/spring-petclinic-rest'
                sleep 30 // seconds
                withCredentials([string(credentialsId: 'postman_api_key', variable: 'TOKEN')]) {
                    sh 'docker run --network=host -t postman/newman run https://api.getpostman.com/collections/14312820-c39aca89-b267-4d97-a2ca-b65df579f9fa?apikey='+TOKEN+' --environment https://api.getpostman.com/environments/14312820-58506620-e644-46ff-a263-1884e7935177?apikey='+TOKEN
                }
            }
        }
        stage('Push docker image') {
            when {
                branch 'master'
            }
            steps {
                script {
                    def prod = load "jobs/production.groovy"
                    //def app = prod.build()
                    prod.push(APP)
                }
            }
        }
        stage('Run docker image on remote server A') {
            when {
                branch 'master'
            }
            steps {
                script {
                    def prod = load "jobs/production.groovy"
                    prod.run('spring-petclinic-rest-A', 9966)
                }
            }
        }
        stage ('Wait') {
            when {
                branch 'master'
            }
            steps {
                echo 'Waiting for container A to start up'
                sleep 30 // seconds
            }
        }
        stage('Run docker image on remote server B') {
            when {
                branch 'master'
            }
            steps {
                script {
                    def prod = load "jobs/production.groovy"
                    prod.run('spring-petclinic-rest-B', 9977)
                }
            }
        }
    }
    post {
        always {
            sh 'docker image prune -f'
            sh 'docker container stop rest-test-container'
        }
    }
}