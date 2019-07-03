pipeline {
    agent any
    environment {
        HARBOR_CREDS = credentials('docker-registry')
        K8S_CONFIG = credentials('k8s-config')
    }
    parameters {
        string(name: 'HARBOR_HOST', defaultValue: 'harbor.olavoice.com', description: 'harbor仓库地址')
        string(name: 'DOCKER_IMAGE', defaultValue: 'test/jenkins-docker-demo', description: 'docker镜像名')
        string(name: 'APP_NAME', defaultValue: 'jenkins-docker-demo', description: 'k8s中标签名')
        string(name: 'K8S_NAMESPACE', defaultValue: 'jenkins-test', description: 'k8s的namespace名称')
        string(name: 'DOCKER_IMAGE_TAG', defaultValue: '1.0', description: 'docker-image-tag')
    }
    stages {
        stage('Maven Build') {
            agent {
                docker {
                    image 'maven:3-jdk-8-alpine'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn clean package -Dfile.encoding=UTF-8 -DskipTests=true'
                stash includes: 'target/*.jar', name: 'app'
            }

        }
        stage('Docker Build') {
            agent any
            steps {
                unstash 'app'
                sh "docker login -u ${HARBOR_CREDS_USR} -p ${HARBOR_CREDS_PSW} ${params.HARBOR_HOST}"
                sh "docker build --build-arg JAR_FILE=`ls target/*.jar |cut -d '/' -f2` -t ${params.HARBOR_HOST}/${params.DOCKER_IMAGE}:${params.DOCKER_IMAGE_TAG} ."
                sh "docker push ${params.HARBOR_HOST}/${params.DOCKER_IMAGE}:${params.DOCKER_IMAGE_TAG} "
                sh "docker rmi ${params.HARBOR_HOST}/${params.DOCKER_IMAGE}:${params.DOCKER_IMAGE_TAG} "
            }

        }
        stage('Deploy') {
            agent {
                docker {
                    image 'lwolf/helm-kubectl-docker'
                }
            }
            steps {
                sh "mkdir -p ~/.kube"
                sh "echo ${K8S_CONFIG} | base64 -d > ~/.kube/config"
                sh "sed -e 's#{IMAGE_URL}#${params.HARBOR_HOST}/${params.DOCKER_IMAGE}#g;s#{IMAGE_TAG}#${params.DOCKER_IMAGE_TAG}#g;s#{APP_NAME}#${params.APP_NAME}#g' k8s-deployment.tpl > k8s-deployment.yml"
                sh "kubectl apply -f k8s-deployment.yml --namespace=${params.K8S_NAMESPACE}"
            }

        }

    }
}