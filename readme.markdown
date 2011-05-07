# SC2Ranks Scala API
## Description
A set of APIs to access [sc2ranks](http://www.sc2ranks.com/). It roughly matches the exposed API except for returning empty lists/collections as opposed to throwing errors if a query did not have any results.



## Example usage

    import rndmlft.sc2ranksapi._
    import rndmlft.sc2ranksapi.characters._

    val api = new CharactersApi("test")
    api.findCharacterWithBaseTeamInfo(CharacterId.fromBNnetId("LMntStallion", 471509), Region.US) match {
      case Full(character) => println("Found: " + character)
      case other => println("Unable to find character: " + other)
    }
