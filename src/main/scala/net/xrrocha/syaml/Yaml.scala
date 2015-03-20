package syaml

import spray.json.RootJsonFormat

object Yaml {
  import scala.util.matching.Regex
  case class Conversion(regex: Regex, converter: String => String)

  val conversions: Seq[Conversion] = Seq(
    Conversion("""^[0-9]*\.[0-9]*$""".r, _.toDouble.toString),
    Conversion("""^[0-9]+$""".r, _.toInt.toString),
    Conversion("""^(null)|(true)|(false)$""".r, s => s),
    Conversion("""^(['"]).*\1$""".r, s => s""""${s.substring(1, s.length - 1)}""""),
    Conversion(""".*$""".r, s => s""""$s"""")
  )

  implicit class YamlString(val sc: StringContext) extends AnyVal {
    def yaml(args: Any*) = new {
      def apply[A](implicit jsonFormat: RootJsonFormat[A]): A = {
        val yaml = sc.s(args: _*).stripMargin.trim
        val map = YamlParser.parse(yaml).get.asInstanceOf[Map[String, Any]]

        def toJson(any: Any): String = any match {
          case list: List[_] => s"[${list.map(e => s"${toJson(e)}").mkString(",")}]"
          case map: Map[_, _] => s"{${map.map(kv => s""""${kv._1}":${toJson(kv._2)}""").mkString(",")}}"
          case _ => {
            val value = any.toString
            conversions
              .find(_.regex.findFirstIn(value).isDefined)
              .map(_.converter(value))
              .get
          }
        }

        import spray.json._

        val jsonString = toJson(map)
        val json = jsonString.parseJson
        json.convertTo[A]
      }
    }
  }
}
