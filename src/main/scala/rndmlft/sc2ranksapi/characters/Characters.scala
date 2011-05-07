package rndmlft.sc2ranksapi.characters

import rndmlft.sc2ranksapi._
import net.liftweb.common._

class CharacterId(name: String, code: Int, separator: Char) {
  override def toString = name + separator + code
}

object CharacterId {
  def fromBNnetId(name: String, id: Int) = new CharacterId(name, id, '!')

  def fromCharacterCode(name: String, code: Int) = new CharacterId(name, code, '$')
}


case class Portrait(icon_id: Int, column: Int, row: Int)

case class Team(world_rank: Int, division: String, division_rank: Int, ratio: String, league: String, fav_race: String, updated_at: String, region_rank: Int, bracket: Int, wins: Int, is_random: Boolean, losses: Int, points: Int)

case class Character(bnet_id: Int, updated_at: String, achievement_points: Int, region: String, portrait: Portrait, name: String, id: Int, character_code: Int, teams: Option[List[Team]])

class CharactersApi(val apiKey: String) extends SC2RanksApi {

  def findBaseCharacter(id: CharacterId, region: Region.Type) = Box[Character] {
    query("base", "char", region, id) {
      _.extractOpt[Character] orElse Failure("Unable to parse response")
    }
  }

  def findCharacterWithBaseTeamInfo(id: CharacterId, region: Region.Type) = Box[Character] {
    query("base", "teams", region, id) {
      _.extractOpt[Character] orElse Failure("Unable to parse response")
    }
  }
}