package db

import io.flow.common.v0.models.User
import io.flow.postgresql.Authorization
import io.flow.registry.v0.models._
import io.flow.play.clients.MockUserTokensClient
import io.flow.play.util.{IdGenerator, UrlKey}
import io.flow.postgresql.OrderBy
import java.util.UUID

trait Helpers {

  lazy val testUser = createUser()
  lazy val testService = ServicesDao.findById(Authorization.All, "test").getOrElse {
    rightOrErrors(ServicesDao.create(testUser, createServiceForm()))
  }

  val idGenerator = IdGenerator("tst")

  def createUrlKey(prefix: String = "tst"): String = {
    prefix +  UUID.randomUUID.toString.replaceAll("\\-", "")
  }

  def createTestId(): String = {
    idGenerator.randomId()
  }

  def createTestName(): String = {
    s"Z Test ${UUID.randomUUID}"
  }

  def createTestEmail(): String = {
    createTestId() + "@test.flow.io"
  }

  def createUser(): User = {
    MockUserTokensClient.makeUser()
  }

  def rightOrErrors[T](result: Either[Seq[String], T]): T = {
    result match {
      case Left(errors) => sys.error(errors.mkString(", "))
      case Right(obj) => obj
    }
  }

  def createService(
    form: ServiceForm = createServiceForm()
  ) (
    implicit user: User = testUser
  ): Service = {
    rightOrErrors(ServicesDao.create(user, form))
  }

  def createServiceForm(): ServiceForm = {
    ServiceForm(
      id = createUrlKey("svc"),
      defaultPort = 8080
    )
  }
  
  def createServicePutForm(): ServicePutForm = {
    ServicePutForm(
      defaultPort = 8080
    )
  }
  
  def createApplication(
    form: ApplicationForm = createApplicationForm()
  ) (
    implicit user: User = testUser
  ): Application = {
    rightOrErrors(ApplicationsDao.create(user, form))
  }

  def createApplicationForm(
    service: Service = testService
  ): ApplicationForm = {
    ApplicationForm(
      id = createUrlKey("app"),
      service = service.id
    )
  }

  def createApplicationPutForm(
    service: Service = testService
  ): ApplicationPutForm = {
    ApplicationPutForm(
      service = service.id
    )
  }

  def createPort(
    form: PortForm = createPortForm()
  ) (
    implicit user: User = testUser
  ): InternalPort = {
    PortsDao.create(user, form)
  }

  def createPortForm(
    application: Application = createApplication()
  ): PortForm = {
    val nextPort: Long = PortsDao.maxExternalPortNumber.getOrElse(6000)

    PortForm(
      applicationId = application.id,
      serviceId = testService.id,
      internal = testService.defaultPort,
      external = nextPort + 1
    )
  }

}
