import com.themillhousegroup.scoup.{ScoupImplicits,Scoup}
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source

object SoftEng extends App with ScoupImplicits {
  val url = Scoup.get("https://en.wikipedia.org/wiki/Category:Software_engineers")
  val waitUrl = Await.result(url,Duration.Inf)
  val docHtml = Scoup.parseHTML(waitUrl)
  val listOfContent = docHtml.select("div ul li").text()
  val partten = "\\d+\\sC".r
  val outPut = partten.findAllMatchIn(listOfContent).toList
  println(outPut)

}

object Scraper extends App with ScoupImplicits{
  val url = Scoup.get("https://www.revenue.alabama.gov/sales-use/state-sales-use-tax-rates/")
  val waitUrl = Await.result(url,Duration.Inf)
  val docHtml = Scoup.parseHTML(waitUrl)
  val parsedCon = docHtml.select(".wp-block-list").head.text()
  val pattern = "\\d,\\d+.\\d+".r
  val rate = pattern.findAllMatchIn(parsedCon).toList

  println(rate)

}

object OhioScraper extends App with ScoupImplicits{
  val url = Scoup.get("https://taxfoundation.org/location/ohio/")
  val waitUrl = Await.result(url,Duration.Inf)
  val docHtml = Scoup.parseHTML(waitUrl)
  val box = docHtml.select("div .data-boxes").head
  val taxType = box.select("h5").toList
  val rate = box.select(".num").toList
  val ans =  Map(
    taxType(0).text() -> rate(0).text(),
    taxType(1).text() -> rate(1).text(),
    taxType(2).text() -> rate(2).text()
  ).toList
  println(ans)
}

object Texas extends App with ScoupImplicits {
  val url = Scoup.get("https://comptroller.texas.gov/taxes/sales/county.php")
  val waitUrl = Await.result(url, Duration.Inf)
  val docHtml = Scoup.parseHTML(waitUrl)
  val tableCon = docHtml.select("table")
  val tableHeader = tableCon.select("thead tr th").map(_.text.trim).toList
  val tableContent = tableCon.select("tbody tr").toList.map { row =>
    val column = row.select("td")
    Map(
      tableHeader.head -> row.select("th").text(),
      tableHeader(1) -> column.get(0).text(),
      tableHeader(2) -> column.get(1).text()
    ).toList
  }
  println(tableContent)
}

object VermontTable extends App with ScoupImplicits {
  val url = Scoup.get("https://taxfoundation.org/location/vermont/#:~:text=Vermont%20Tax%20Rates%2C%20Collections%2C%20and%20Burdens&text=Vermont%20has%20a%20graduated%20corporate,tax%20rate%20of%206.36%20percent.")
  val waitUrl = Await.result(url, Duration.Inf)
  val docHtml = Scoup.parseHTML(waitUrl)
  val box = docHtml.select("div .data-boxes").head
  val boxHead = box.select(".data-boxes__item h5").toList
  val boxCon = box.select(".num").toList
  val ans = Map(
    boxHead(0).text() -> boxCon(0).text(),
    boxHead(1).text() -> boxCon(1).text(),
    boxHead(2).text() -> boxCon(2).text()
  ).toList

  println(ans)
}

object MississippiTax extends App with ScoupImplicits {
  val url = Scoup.get("https://www.dor.ms.gov/business/tax-notices/sales-and-use-tax-notices")
  val waitUrl = Await.result(url,Duration.Inf)
  val parseUrl = Scoup.parseHTML(waitUrl)
  val content = parseUrl.select("tbody tr td a")
  println(content)

}
