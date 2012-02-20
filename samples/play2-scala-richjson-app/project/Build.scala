import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "play2-scala-richjson-app"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "play.tools.richjson" %% "play-2.0-scala-richjson-tool" % "0.1-SNAPSHOT"
    )
    // need to update to your own repo
    //val moduleRepo = "local repository" at "file:///path/to/your/local-repo"
    val moduleRepo = Resolver.file("local repository", file("/Users/pvo/.ivy2/local"))(Resolver.ivyStylePatterns)

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      resolvers += moduleRepo,
      resolvers += ("typesafe snapshots" at "http://repo.typesafe.com/typesafe/snapshots/")
    )

}
