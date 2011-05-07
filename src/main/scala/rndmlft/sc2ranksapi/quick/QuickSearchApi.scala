package rndmlft.sc2ranksapi.quick

import rndmlft.sc2ranksapi._
import net.liftweb.common._

case class Character(bnet_id: Int, name: String)

case class QuickSearchResult(total: Int, characters: List[Character])

class QuickSearchApi(val apiKey: String) extends SC2RanksApi {

  def search(name: String, matchType: MatchType.Type, region: Region.Type): Box[QuickSearchResult] = search(name, matchType, region, 0)

  def search(name: String, matchType: MatchType.Type, region: Region.Type, offset: Int): Box[QuickSearchResult] = {
    query("search", matchType, region, name, offset.toString + ".json") {
      _.extractOpt[QuickSearchResult].orElse(Full(QuickSearchResult(0, Nil)))
    }
  }
}