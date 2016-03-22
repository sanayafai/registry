/**
 * Generated by apidoc - http://www.apidoc.me
 * Service version: 0.0.49
 * apidoc:0.11.21 http://www.apidoc.me/flow/registry/0.0.49/anorm_2_x_parsers
 */
package io.flow.registry.v0.anorm.conversions {

  import anorm.{Column, MetaDataItem, TypeDoesNotMatch}
  import play.api.libs.json.{JsArray, JsObject, JsValue}
  import scala.util.{Failure, Success, Try}

  /**
    * Conversions to collections of objects using JSON.
    */
  object Json {

    def parser[T](
      f: play.api.libs.json.JsValue => T
    ) = anorm.Column.nonNull1 { (value, meta) =>
      val MetaDataItem(qualified, nullable, clazz) = meta
      value match {
        case json: org.postgresql.util.PGobject => {
          Try {
            f(
              play.api.libs.json.Json.parse(
                json.getValue
              )
            )
          } match {
            case Success(result) => Right(result)
            case Failure(ex) => Left(
              TypeDoesNotMatch(
                s"Column[$qualified] error parsing json $value: $ex"
              )
            )
          }
        }
        case _=> {
          Left(
            TypeDoesNotMatch(
              s"Column[$qualified] error converting $value: ${value.asInstanceOf[AnyRef].getClass} to Json"
            )
          )
        }


      }
    }

    implicit val columnToJsObject: Column[play.api.libs.json.JsObject] = parser { _.as[play.api.libs.json.JsObject] }

    implicit val columnToSeqBoolean: Column[Seq[Boolean]] = parser { _.as[Seq[Boolean]] }
    implicit val columnToMapBoolean: Column[Map[String, Boolean]] = parser { _.as[Map[String, Boolean]] }
    implicit val columnToSeqDouble: Column[Seq[Double]] = parser { _.as[Seq[Double]] }
    implicit val columnToMapDouble: Column[Map[String, Double]] = parser { _.as[Map[String, Double]] }
    implicit val columnToSeqInt: Column[Seq[Int]] = parser { _.as[Seq[Int]] }
    implicit val columnToMapInt: Column[Map[String, Int]] = parser { _.as[Map[String, Int]] }
    implicit val columnToSeqLong: Column[Seq[Long]] = parser { _.as[Seq[Long]] }
    implicit val columnToMapLong: Column[Map[String, Long]] = parser { _.as[Map[String, Long]] }
    implicit val columnToSeqLocalDate: Column[Seq[_root_.org.joda.time.LocalDate]] = parser { _.as[Seq[_root_.org.joda.time.LocalDate]] }
    implicit val columnToMapLocalDate: Column[Map[String, _root_.org.joda.time.LocalDate]] = parser { _.as[Map[String, _root_.org.joda.time.LocalDate]] }
    implicit val columnToSeqDateTime: Column[Seq[_root_.org.joda.time.DateTime]] = parser { _.as[Seq[_root_.org.joda.time.DateTime]] }
    implicit val columnToMapDateTime: Column[Map[String, _root_.org.joda.time.DateTime]] = parser { _.as[Map[String, _root_.org.joda.time.DateTime]] }
    implicit val columnToSeqBigDecimal: Column[Seq[BigDecimal]] = parser { _.as[Seq[BigDecimal]] }
    implicit val columnToMapBigDecimal: Column[Map[String, BigDecimal]] = parser { _.as[Map[String, BigDecimal]] }
    implicit val columnToSeqJsObject: Column[Seq[_root_.play.api.libs.json.JsObject]] = parser { _.as[Seq[_root_.play.api.libs.json.JsObject]] }
    implicit val columnToMapJsObject: Column[Map[String, _root_.play.api.libs.json.JsObject]] = parser { _.as[Map[String, _root_.play.api.libs.json.JsObject]] }
    implicit val columnToSeqString: Column[Seq[String]] = parser { _.as[Seq[String]] }
    implicit val columnToMapString: Column[Map[String, String]] = parser { _.as[Map[String, String]] }
    implicit val columnToSeqUUID: Column[Seq[_root_.java.util.UUID]] = parser { _.as[Seq[_root_.java.util.UUID]] }
    implicit val columnToMapUUID: Column[Map[String, _root_.java.util.UUID]] = parser { _.as[Map[String, _root_.java.util.UUID]] }

    import io.flow.registry.v0.models.json._
    implicit val columnToSeqRegistryApplication: Column[Seq[_root_.io.flow.registry.v0.models.Application]] = parser { _.as[Seq[_root_.io.flow.registry.v0.models.Application]] }
    implicit val columnToMapRegistryApplication: Column[Map[String, _root_.io.flow.registry.v0.models.Application]] = parser { _.as[Map[String, _root_.io.flow.registry.v0.models.Application]] }
    implicit val columnToSeqRegistryApplicationForm: Column[Seq[_root_.io.flow.registry.v0.models.ApplicationForm]] = parser { _.as[Seq[_root_.io.flow.registry.v0.models.ApplicationForm]] }
    implicit val columnToMapRegistryApplicationForm: Column[Map[String, _root_.io.flow.registry.v0.models.ApplicationForm]] = parser { _.as[Map[String, _root_.io.flow.registry.v0.models.ApplicationForm]] }
    implicit val columnToSeqRegistryApplicationPutForm: Column[Seq[_root_.io.flow.registry.v0.models.ApplicationPutForm]] = parser { _.as[Seq[_root_.io.flow.registry.v0.models.ApplicationPutForm]] }
    implicit val columnToMapRegistryApplicationPutForm: Column[Map[String, _root_.io.flow.registry.v0.models.ApplicationPutForm]] = parser { _.as[Map[String, _root_.io.flow.registry.v0.models.ApplicationPutForm]] }
    implicit val columnToSeqRegistryApplicationVersion: Column[Seq[_root_.io.flow.registry.v0.models.ApplicationVersion]] = parser { _.as[Seq[_root_.io.flow.registry.v0.models.ApplicationVersion]] }
    implicit val columnToMapRegistryApplicationVersion: Column[Map[String, _root_.io.flow.registry.v0.models.ApplicationVersion]] = parser { _.as[Map[String, _root_.io.flow.registry.v0.models.ApplicationVersion]] }
    implicit val columnToSeqRegistryPort: Column[Seq[_root_.io.flow.registry.v0.models.Port]] = parser { _.as[Seq[_root_.io.flow.registry.v0.models.Port]] }
    implicit val columnToMapRegistryPort: Column[Map[String, _root_.io.flow.registry.v0.models.Port]] = parser { _.as[Map[String, _root_.io.flow.registry.v0.models.Port]] }
    implicit val columnToSeqRegistryService: Column[Seq[_root_.io.flow.registry.v0.models.Service]] = parser { _.as[Seq[_root_.io.flow.registry.v0.models.Service]] }
    implicit val columnToMapRegistryService: Column[Map[String, _root_.io.flow.registry.v0.models.Service]] = parser { _.as[Map[String, _root_.io.flow.registry.v0.models.Service]] }
    implicit val columnToSeqRegistryServiceForm: Column[Seq[_root_.io.flow.registry.v0.models.ServiceForm]] = parser { _.as[Seq[_root_.io.flow.registry.v0.models.ServiceForm]] }
    implicit val columnToMapRegistryServiceForm: Column[Map[String, _root_.io.flow.registry.v0.models.ServiceForm]] = parser { _.as[Map[String, _root_.io.flow.registry.v0.models.ServiceForm]] }
    implicit val columnToSeqRegistryServicePutForm: Column[Seq[_root_.io.flow.registry.v0.models.ServicePutForm]] = parser { _.as[Seq[_root_.io.flow.registry.v0.models.ServicePutForm]] }
    implicit val columnToMapRegistryServicePutForm: Column[Map[String, _root_.io.flow.registry.v0.models.ServicePutForm]] = parser { _.as[Map[String, _root_.io.flow.registry.v0.models.ServicePutForm]] }
    implicit val columnToSeqRegistryServiceReference: Column[Seq[_root_.io.flow.registry.v0.models.ServiceReference]] = parser { _.as[Seq[_root_.io.flow.registry.v0.models.ServiceReference]] }
    implicit val columnToMapRegistryServiceReference: Column[Map[String, _root_.io.flow.registry.v0.models.ServiceReference]] = parser { _.as[Map[String, _root_.io.flow.registry.v0.models.ServiceReference]] }
    implicit val columnToSeqRegistryServiceVersion: Column[Seq[_root_.io.flow.registry.v0.models.ServiceVersion]] = parser { _.as[Seq[_root_.io.flow.registry.v0.models.ServiceVersion]] }
    implicit val columnToMapRegistryServiceVersion: Column[Map[String, _root_.io.flow.registry.v0.models.ServiceVersion]] = parser { _.as[Map[String, _root_.io.flow.registry.v0.models.ServiceVersion]] }

  }

}