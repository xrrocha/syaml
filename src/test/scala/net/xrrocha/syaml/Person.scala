package syaml

trait Level
case object Native extends Level
case object Professional extends Level

case class LanguageSkill(language: String, level: Level)

case class Person(id: Int, name: String, value: Double, score: String, languageSkills: Seq[LanguageSkill] = Seq())

import spray.json._

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit object LevelJsonFormat extends RootJsonFormat[Level] {
    def write(level: Level) = JsString { level match {
        case Native => "Native"
        case Professional => "Professional"
      }
    }

    def read(value: JsValue) = value match {
      case JsString(value) => value match {
        case "Native" => Native
        case "Professional" => Professional
        case _ => deserializationError("Level expected")
      }

      case _ => deserializationError("Level expected")
    }
  }
  implicit val languageSkillFormat = jsonFormat2(LanguageSkill.apply)
  implicit val personFormat = jsonFormat5(Person.apply)
}
