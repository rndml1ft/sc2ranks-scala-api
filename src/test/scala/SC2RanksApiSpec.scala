package rndmlft.sc2ranksapi


import net.liftweb.common._
import org.scalatest.Spec


class SC2RanksApiSpec extends Spec {

  describe("a profiles api instance") {
    import profiles._

    val api = new ProfilesApi("test")

    it("should find some profiles with the name 'shadow' and ~500 points") {

      val results = api.search("shadow", SearchCategory.OneVsOne, SearchSubType.Points, "500", Region.US)

      results match {
        case Full(profiles) => assert(profiles.size > 10)
        case other => fail("expected profiles, got " + other)
      }
    }
  }

  describe("a quicksearch api instance") {
    import quick._

    val api = new QuickSearchApi("test");

    it("should return an empty list if user is not found") {
      val results = api.search("anonexistingusername", MatchType.Exact, Region.US)

      results match {
        case Full(searchResult) =>
          assert(searchResult.total === 0)
          assert(searchResult.characters.isEmpty)
        case other => fail("expected empty result, got " + other)
      }
    }

    it("should find lots of characters with 'shadow' in the name") {

      val results = api.search("shadow", MatchType.Contains, Region.US)

      results match {
        case Full(searchResult) => assert(searchResult.total > 1000)
        case other => fail("expected lots of character matches, got " + other)
      }
    }

  }

  describe("a characters api instance") {
    import characters._

    val api = new CharactersApi("test")

    it("should return an empty box if base character was not found") {
      val results = api.findBaseCharacter(CharacterId.fromBNnetId("anonexistinguser", 0), Region.US)
      assert(results.isEmpty)
    }

    it("should find a specific base character with name 'shadow' and bnet_id '710059'") {
      val results = api.findBaseCharacter(CharacterId.fromBNnetId("shadow", 710059), Region.US)
      results match {
        case Full(base) =>
          assert(base.id === 37950)
          assert(base.character_code === 591)
        case other => fail("expected base character, got " + other)
      }
    }

    it("should find a specific base character with name 'shadow' and character code '591'") {
      val results = api.findBaseCharacter(CharacterId.fromCharacterCode("ShaDow", 591), Region.US)
      results match {
        case Full(base) => assert(base.bnet_id === 710059)
        case other => fail("expected base character, got " + other)
      }
    }

    it("should return an empty box if base character with team info was not found") {
      val results = api.findCharacterWithBaseTeamInfo(CharacterId.fromBNnetId("anonexistinguser", 0), Region.US)
      assert(results.isEmpty)
    }

    it("should find a specific base character with name 'LMntStallion' and bnet_id '471509'") {
      val results = api.findCharacterWithBaseTeamInfo(CharacterId.fromBNnetId("LMntStallion", 471509), Region.US)
      results match {
        case Full(char) =>
          assert(char.bnet_id === 471509)
          assert(char.teams.isDefined)
        case other => fail("expected base character, got " + other)
      }
    }
  }
}