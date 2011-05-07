package rndmlft.sc2ranksapi

import dispatch._
import dispatch.HandlerVerbs._
import net.liftweb.json._
import net.liftweb.common._


object Region extends Enumeration("us", "eu", "kr", "tw", "sea", "ru", "la") {
  type Type = Value
  val US, EU, KR, TW, SEA, RU, LA = Value
}

object SearchCategory extends Enumeration("1t", "2t", "3t", "4t", "achieve") {
  type Type = Value
  val OneVsOne, TwoVsTwo, ThreeVsThree, FourVsFour, Achievements = Value
}

object MatchType extends Enumeration("exact", "contains", "starts", "ends") {
  type Type = Value
  val Exact, Contains, Starts, Ends = Value
}


trait SC2RanksApi {

  implicit val formats = net.liftweb.json.DefaultFormats

  val apiKey: String

  lazy val http = new Http

  def apiUrl = :/("sc2ranks.com") / "api"

  def query[T](args: scala.Any*)(f: JValue => Box[T]): Box[T] = {
    val req = args.foldLeft(apiUrl)((url, arg) => url / arg.toString) <<? Map("appKey" -> apiKey)
    val json: JValue = http(req >- JsonParser.parse)
    //println(pretty(render(json)))

    (json \ "error") match {
      case JString("no_characters") => f(JNothing)
      case JString(errorMessage) => Failure(errorMessage)
      case _ => f(json)
    }
  }
}