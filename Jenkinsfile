pipeline {
    agent none

    stages {
        stage('Clone Code') {
            agent {
                label 'master'
            }
            steps {
                echo "1.Git Clone Code"
                git url: "https://github.com/Rayendelin/cloud-native-project.git"

            }
        }

        stage('Maven Build') {
            agent {
                docker {
                    image 'maven:latest'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                echo "2.Maven Build Stage"
                sh 'mvn -B clean package -Dmaven.test.skip=true'
            }
        }

        stage('Image Build') {
            agent {
                label 'master'
            }
            steps {
                echo "3.Image Build Stage"
                sh 'docker build -f Dockerfile --build-arg jar_name=target/cloud-native-project-0.0.1-SNAPSHOT.jar -t cloud-native-project:${BUILD_ID} . '
                sh 'docker tag cloud-native-project:${BUILD_ID} harbor.edu.cn/nju03/cloud-native-project:${BUILD_ID}'
            }
        }

        stage('Push') {
            agent {
                label 'master'
            }
            steps {
                echo "4.Push Docker Image Stage"
                sh 'docker login harbor.edu.cn -u nju03 -p nju032022'
                sh "docker push harbor.edu.cn/nju03/cloud-native-project:${BUILD_ID}"
            }
        }

    }
}

node('slave') {
    container('jnlp-kubectl') {

        stage('Clone YAML') {
             echo "5. Git Clone YAML To Slave"
             git url: "https://github.com/Rayendelin/cloud-native-project.git"
        }

        stage('YAML') {
             echo "6. Change YAML File Stage"
             sh 'sed -i "s#{VERSION}#${BUILD_ID}#g" ./config/deploy-svc.yaml'
        }

        stage('Deploy') {
             echo "7. Deploy To K8s Stage"
             sh 'kubectl apply -f deploy-svc.yaml'
        }
    }
}