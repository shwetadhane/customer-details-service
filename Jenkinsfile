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
       stage('sonar-scanner analysis') {
             def sonarqubeScannerHome = tool name: 'sonar-scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
             withCredentials([string(credentialsId: 'sonar', variable: 'sonarLogin')]) {
                 sh "${sonarqubeScannerHome}/bin/sonar-scanner -X -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=${sonarLogin} -Dsonar.projectName=${env.JOB_NAME} -Dsonar.projectVersion=${env.BUILD_NUMBER} -Dsonar.projectKey=${env.JOB_BASE_NAME} -Dsonar.sources=src/main/java -Dsonar.java.libraries=target/* -Dsonar.java.binaries=target/classes -Dsonar.language=java"
             }
             sh "sleep 40"
             env.WORKSPACE = pwd()
             def file = readFile "${env.WORKSPACE}/.scannerwork/report-task.txt"
             echo file.split("\n")[5]
    
             def resp = httpRequest file.split("\n")[5].split("l=")[1]
    
            ceTask = readJSON text: resp.content
            echo ceTask.toString()
            def response2 = httpRequest url : 'http://sonarqube:9000' + "/api/qualitygates/project_status?analysisId=" + ceTask["task"]["analysisId"]
            def qualitygate =  readJSON text: response2.content
            echo qualitygate.toString()
            if ("ERROR".equals(qualitygate["projectStatus"]["status"])) {
               echo "Build Failed"
            }
            else {
               echo "Build Passed"
            }
       }

//         stage('Deploy App to Kubernetes') {
//             steps{
//                 script {
//                     kubernetesDeploy( configs: "customer-details-service-k8s-deployment.yaml", kubeconfigId: "k8s")
//                     kubernetesDeploy( configs: "customer-details-service-k8s-svc.yaml", kubeconfigId: "k8s")
//                 }
//             }
//         }
        stage('Rollout Latest changes to k8s') {
            steps{
//                withKubeConfig([credentialsId: 'jenkins-robot-global']) {
                    sh 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/v1.20.5/bin/linux/amd64/kubectl"'
                    sh 'chmod u+x ./kubectl'
                    sh './kubectl apply -f customer-details-service-k8s-deployment.yaml'
                    sh './kubectl apply -f customer-details-service-k8s-svc.yaml'
                    sh './kubectl rollout restart deployment customer-details-service -n default'
                    // sh './kubectl get pods'
//                }
            }
          }
    }
}
