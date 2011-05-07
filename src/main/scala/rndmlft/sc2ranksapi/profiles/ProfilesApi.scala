package rndmlft.sc2ranksapi.profiles

import rndmlft.sc2ranksapi._
import net.liftweb.common._
import net.liftweb.common.Failure._

case class Team(wins: Int, division_id: Int, points: Int, losses: Int, id: Int, division_name: String)

case class SearchResult(bnet_id: Int, region: String, achievement_points: Int, name: String, id: Int, character_code: Int, team: Team)


object SearchSubType extends Enumeration("points", "wins", "losses", "division") {
  type Type = Value
  val Points, Wins, Losses, Division = Value
}


class ProfilesApi(val apiKey: String) extends SC2RanksApi {
  def search(name: String, searchType: SearchCategory.Type, subType: SearchSubType.Type, subTypeValue: String, region: Region.Type): Box[List[SearchResult]] = {
    query("psearch", region, name, searchType, subType, subTypeValue + ".json") {
      _.extractOpt[List[SearchResult]] orElse Failure("Unable to parse response")
    }
  }
}

