import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2021.2"

project {

    vcsRoot(HttpsGithubComKenjiruTeamcityConfigRefsHeadsMaster)

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    publishArtifacts = PublishMode.SUCCESSFUL

    vcs {
        root(HttpsGithubComKenjiruTeamcityConfigRefsHeadsMaster)
    }

    steps {
        nodeJS {
            shellScript = """
                whoami
                npm version
                npm install
                npm run build
            """.trimIndent()
            dockerRunParameters = "-u 1000"
        }
    }

    triggers {
        vcs {
        }
    }
})

object HttpsGithubComKenjiruTeamcityConfigRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/kenjiru/teamcity-config#refs/heads/master"
    pollInterval = 5
    url = "https://github.com/kenjiru/teamcity-config"
    branch = "refs/heads/master"
    branchSpec = """
        +:refs/heads/*
        -:refs/heads/teamcity-config
    """.trimIndent()
    authMethod = password {
        userName = "kenjiru"
        password = "credentialsJSON:949ed43e-21b4-4e0b-a2ce-daad0fec6554"
    }
})
