package uk.gov.hmrc.viewmodels

import play.api.libs.json.{Json, OWrites}

final case class Fieldset (
                            legend: Option[Legend] = None,
                            describedBy: Option[String] = None,
                            classes: Option[String] = None,
                            attributes: Map[String, String] = Map.empty,
                            called: Option[String] = None
                          )

object Fieldset {

  implicit lazy val writes: OWrites[Fieldset] = OWrites {
    fieldset =>

      Json.obj(
        "legend"      -> fieldset.legend,
        "describedBy" -> fieldset.describedBy,
        "classes"     -> fieldset.classes,
        "attributes"  -> fieldset.attributes,
        "called"      -> fieldset.called
      ).filterNulls
  }
}
