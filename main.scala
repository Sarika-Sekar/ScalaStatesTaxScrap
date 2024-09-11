import com.themillhousegroup.scoup.{Scoup, ScoupImplicits}
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.io.Source

object MississippiTax extends App with ScoupImplicits {
  val url = Scoup.get("https://www.dor.ms.gov/business/tax-notices/sales-and-use-tax-notices")
  val waitUrl = Await.result(url,Duration.Inf)
  val parseUrl = Scoup.parseHTML(waitUrl)
  val content = parseUrl.select("tbody tr td a")
  println(content)

}

