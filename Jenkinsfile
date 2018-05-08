pipeline {
    agent any
    
    environment { 
        DEPLOY_TARGET    = '../../../webapps/wf.war'
        EMAIL_RECIPIENTS = 'zb@bndy.net'
        HAS_DEPLOYMENT   = 'false'
    }

    stages {
    	stage('Prepare') {
    		steps {
				sh 'printenv'
				sh 'java -version'
         		sh 'mvn -v'

    			echo 'Pulling $BRANCH_NAME...'
				checkout scm
    		}
    	}
        stage('Build') {
            steps {
                echo 'Building...'
                sh 'mvn -B -DskipTests clean package' 
            }
        }
        stage('Test') {
            steps {
                echo 'Skip tests'
            }
        }
        stage('Deploy') {
			when {
                branch 'master'
            }
            steps {
                echo 'Deploying....'
                sh 'rm -f $DEPLOY_TARGET'
                sh 'mv ./target/wf-*.war $DEPLOY_TARGET'
                HAS_DEPLOYMENT = 'true'
            }
        }
    }
    post {
    	always  {
    	}
        success {
            sendEmail("SUCCESS");
        }
        unstable {
            sendEmail("UNSTABLE");
        }
        failure {
            sendEmail("FAILURE");
        }
   	}
}

// get change log to be send over the mail
@NonCPS
def getChangeString() {
    MAX_MSG_LEN = 100
    def changeString = ""

    echo "Gathering SCM changes"
    def changeLogSets = currentBuild.changeSets
    for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
            def entry = entries[j]
            truncated_msg = entry.msg.take(MAX_MSG_LEN)
            changeString += " - ${truncated_msg} [${entry.author}]\n"
        }
    }

    if (!changeString) {
        changeString = " - No new changes"
    }
    return changeString
}

def sendEmail(status) {
	def subject = "[" + status + "] CI - ${currentBuild.fullDisplayName}"
	if (HAS_DEPLOYMENT == 'true') {
		subject += " - deployed"
	}
    mail(
		to: "$EMAIL_RECIPIENTS",
		subject: "${subject}",
		body: "Changes:\n " + getChangeString() + "\n\n Check console output at: $BUILD_URL/console" + "\n"
	)
}