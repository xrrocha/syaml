package syaml

import org.scalatest.FunSuite

class YamlParserTest extends FunSuite {
  import Yaml._
  
  test("Creates case class instance correctly") {
    import MyJsonProtocol._
    
      val person = yaml"""
          id: 7
          name: Neo Anderson
          value: 3.1416
          score: '0'
          languageSkills: [{language: Spanish, level: Native}, {language: English, level: Professional}]
        """[Person]
        
      assert(person.id == 7)
      assert(person.name == "Neo Anderson")
      assert(person.value == 3.1416)
      assert(person.score == "0")
      assert(person.languageSkills.length == 2)
  }
}