import sbt._

class SC2RanksLibProject(info: ProjectInfo) extends DefaultProject(info)
{
  val dispatch = "net.databinder" %% "dispatch-http" % "0.8.0"
  
  val liftVersion = "2.3" 
  val liftJson = "net.liftweb" %% "lift-json" % liftVersion
  val liftCommon = "net.liftweb" %% "lift-common" % liftVersion

  val scalatest = "org.scalatest" % "scalatest" % "1.3"
  val junit = "junit" % "junit" % "4.8.2"
}

