pipeline {

  options {
    ansiColor('xterm')
  }
    tools{
        maven 'maven'
    }


      agent any
    stages {
        stage('Checkout Source ') {
            steps {
                git 'https://github.com/shwetadhane/customer-details-service.git'
            }
        }

        stage ('Build') {
            steps {
                sh 'mvn -DskipTests=true clean install'
            }
        }
         stage('Deploy App to Kubernetes') {
             steps{
                 script {
                     kubernetesDeploy( configs: "customer-details-service-k8s-deployment.yaml", kubeconfigId: "k8s")
                     kubernetesDeploy( configs: "customer-details-service-k8s-svc.yaml", kubeconfigId: "k8s")
                 }
             }
         }
        stage('Rollout Latest changes to k8s') {
            steps{
                withKubeConfig([credentialsId: 'jenkins-robot-global']) {
                    sh 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/v1.20.5/bin/linux/amd64/kubectl"'
                    sh 'chmod u+x ./kubectl'
//                    sh './kubectl apply -f customer-details-service-k8s-deployment.yaml'
//                    sh './kubectl apply -f customer-details-service-k8s-svc.yaml'
                    sh './kubectl rollout restart deployment customer-details-service -n default'
                    // sh './kubectl get pods'
                }
            }
          }
    }
}