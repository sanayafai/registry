package db

import io.flow.registry.v0.models.{Application, ApplicationPutForm, PortType}
import io.flow.postgresql.Authorization
import java.util.UUID
import play.api.test._
import play.api.test.Helpers._
import org.scalatest._
import org.scalatestplus.play._

class ApplicationsDaoSpec extends PlaySpec with OneAppPerSuite with Helpers {

  import scala.concurrent.ExecutionContext.Implicits.global

  def validatePort(modulus: Int, app: Application) {
    app.ports.size must be(1)
    app.ports.foreach { p =>
      if (p.num % 10 != modulus) {
        fail(s"Application port of type[${p.`type`}] port[${p.num}] must end in ${modulus}")
      }
    }
  }

  "respects application type when allocating ports" in {
    val base = UUID.randomUUID.toString.replaceAll("\\-", "")

    validatePort(
      0,
      createApplication(
        createApplicationForm().copy(
          id = base + "-ui",
          `type` = Seq(PortType.Ui)
        )
      )
    )

    validatePort(
      1,
      createApplication(
        createApplicationForm().copy(
          id = base + "-api",
          `type` = Seq(PortType.Api)
        )
      )
    )

    validatePort(
      9,
      createApplication(
        createApplicationForm().copy(
          id = base + "-db",
          `type` = Seq(PortType.Database)
        )
      )
    )


  }

  "allocates ports based on type" in {
    val base = UUID.randomUUID.toString.replaceAll("\\-", "")

    val ui = createApplication(createApplicationForm().copy(id = base + "-ui", `type` = Seq(PortType.Ui)))
    val api = createApplication(createApplicationForm().copy(id = base + "-api", `type` = Seq(PortType.Api)))
    val postgresql = createApplication(createApplicationForm().copy(id = base + "-db", `type` = Seq(PortType.Database)))

    val uiPort = ui.ports.map(_.num).headOption.getOrElse {
      sys.error("Failed to allocate port")
    }

    (uiPort % 10) must be(0)
    api.ports.map(_.num) must be(Seq(uiPort + 1))
    postgresql.ports.map(_.num) must be(Seq(uiPort + 9))
  }

  "allocates block ranges in sets of 10" in {
    val base = UUID.randomUUID.toString.replaceAll("\\-", "")

    val api = createApplication(createApplicationForm().copy(id = base))

    // inject the other app here so we have to 'jump' over its allocation
    val other = createApplication(createApplicationForm().copy(id = UUID.randomUUID.toString.replaceAll("\\-", "")))

    val api2 = createApplication(createApplicationForm().copy(id = base + "-api2"))

    val apiPort = api.ports.map(_.num).headOption.getOrElse {
      sys.error("Failed to allocate port")
    }

    val otherPort = other.ports.map(_.num).headOption.getOrElse {
      sys.error("Failed to allocate port for other app")
    }

    val api2Port = api2.ports.map(_.num).headOption.getOrElse {
      sys.error("Failed to allocate port")
    }

    val offset = otherPort - apiPort + 10
    (offset % 10) must be(0)

    Seq(apiPort + offset, apiPort + offset + 10).contains(api2Port) must be(true)
  }

  "deleting an application also deletes its ports" in {
    val app = createApplication()
    val portNumber = app.ports.map(_.num).headOption.getOrElse {
      sys.error("No port for application")
    }
    PortsDao.findByNumber(Authorization.All, portNumber).getOrElse {
      sys.error("Could not find port")
    }

    ApplicationsDao.softDelete(testUser, app)
    ApplicationsDao.findById(Authorization.All, app.id) must be(None)
    PortsDao.findByNumber(Authorization.All, portNumber) must be(None)
  }

  "upsert creates new application" in {
    val id = createApplicationForm().id
    val app = rightOrErrors(
      ApplicationsDao.upsert(testUser, id, ApplicationPutForm(`type` = Seq(PortType.Api)))
    )

    app.id must be(id)
  }

  "upsert allocates new ports" in {
    val app = createApplication(createApplicationForm().copy(`type` = Seq(PortType.Ui)))
    val portNumber = app.ports.map(_.num).headOption.getOrElse {
      sys.error("No port for application")
    }
    app.ports.map(_.num) must be(Seq(portNumber))

    val updated = rightOrErrors(
      ApplicationsDao.upsert(
        testUser,
        app.id,
        ApplicationPutForm(`type` = Seq(PortType.Api))
      )
    )
    updated.ports.map(_.num) must be(Seq(portNumber, portNumber + 1))

    // Now test idempotence
    val updatedAgain = rightOrErrors(
      ApplicationsDao.upsert(
        testUser,
        app.id,
        ApplicationPutForm(`type` = Seq(PortType.Api))
      )
    )
    updatedAgain.ports.map(_.num) must be(Seq(portNumber, portNumber + 1))
  }

    /*
// TODO Support this use case
  "can reuse ID once deleted" in {
    val app = createApplication()
    ApplicationsDao.softDelete(testUser, app)
    val app2 = createApplication(createApplicationForm().copy(id = app.id))
  }
   */
}
