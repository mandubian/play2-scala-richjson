package play.tools.richjson

import play.api.libs.json._

object RichJson {
  case class JsImplicit[T](value: T) {
    def \:(key: String)(implicit tjv: Writes[T]):JsObject = JsObject(Seq((key, tjv.writes(value))))
    def @@[U](u: U)(implicit tjv: Writes[T], tju: Writes[U]): JsArray = JsArray(List(tjv.writes(value), tju.writes(u)))
  }

  case class JsArrayImplicit(value: JsArray) {
    def @@[T](v: T)(implicit tjv: Writes[T]): JsArray = value :+ tjv.writes(v)
  }

  implicit def jsValue2JsArrayImplicit(value: JsArray): JsArrayImplicit = JsArrayImplicit(value)
  implicit def jsValue2JsImplicit[T](value: T): JsImplicit[T] = JsImplicit(value)
  
  /**
* Serializer for JsArrays.
*/
  implicit object JsArrayWrites extends Writes[JsArray] {
    def writes(o: JsArray) = o
  }
}
