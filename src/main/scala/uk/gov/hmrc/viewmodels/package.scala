package uk.gov.hmrc

import play.api.libs.json.{JsArray, JsNull, JsObject, JsValue, Json}

package object viewmodels {

  implicit class RichJsObject(obj: JsObject) {

    def map(f: (String, JsValue) => (String, JsValue)): JsObject =
      JsObject(obj.fields.map(f.tupled))

    def filter(f: (String, JsValue) => Boolean): JsObject =
      JsObject(obj.fields.filter(f.tupled))

    def filterNot(f: (String, JsValue) => Boolean): JsObject =
      JsObject(obj.fields.filterNot(f.tupled))

    def filterNulls: JsObject = {
      map {
        case (k, v: JsObject) =>
          k -> v.filterNulls
        case (k, v: JsArray) =>
          k -> v.filterNulls
        case (k, v) =>
          k -> v
      }.filterNot((_, value) => value == JsNull || value == Json.obj())
    }
  }

  implicit class RichJsArray(arr: JsArray) {

    def map(f: JsValue => JsValue): JsArray =
      JsArray(arr.value.map(f))

    def filter(f: JsValue => Boolean): JsArray =
      JsArray(arr.value.filter(f))

    def filterNot(f: JsValue => Boolean): JsArray =
      JsArray(arr.value.filterNot(f))

    def filterNulls: JsArray = {
      map {
        case v: JsObject => v.filterNulls
        case v: JsArray  => v.filterNulls
        case v => v
      }.filterNot(v => v == JsNull || v == Json.obj())
    }
  }
}