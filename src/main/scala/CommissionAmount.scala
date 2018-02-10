/**
  * Created by sharadbagal on 4/2/17.
  */
import java.sql.DriverManager
import com.typesafe.config._

case class Employees(first_name: String,
                      last_name: String,
                      salary: Double,
                      commission_pct: Double
                    ) {

  override def toString(): String = {
    s"first_name: " + first_name +";" + "last_name: "+ last_name + ";" + "salary: " + salary +
      ";" + "commission Amount: " + getCommissionAmount + "."
  }

  def getCommissionAmount(): Any ={
    if(commission_pct == 0)
      print("Not Eligible \n")
      else salary * commission_pct
  }
}


object CommissionAmount {


  def main(args: Array[String]): Unit ={
    //val parsedConfig = ConfigFactory.parseFile(new File("src/main/resources/application.config"))
    val props = ConfigFactory.load()

    val hostname = props.getConfig(args(0)).getString("hostname")
    val port = props.getConfig(args(0)).getString("port")
    val user = props.getConfig(args(0)).getString("username")
    val pwd = props.getConfig(args(0)).getString("password")
    val db = props.getConfig(args(0)).getString("db")

    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://"+ hostname +":"+ port +"/"+ db

    Class.forName(driver);
    val connection = DriverManager.getConnection(url,user,pwd)
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(s"Select first_name, last_name, salary, " +
      "commission_pct From employees")

    Iterator.continually((resultSet,resultSet.next)).
          takeWhile(rec => rec._2).
          map(_._1).map(rec => {
          Employees(rec.getString("first_name"),
            rec.getString("last_name"),
            rec.getDouble("salary"),
            rec.getDouble("commission_pct"))
    }).foreach(rec => {
      println(rec)
    })


    /*
      while (resultSet.next()){
        val e = Employees(resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getDouble("salary"),
                resultSet.getDouble("commission_pct"))

        println(e)
      }*/

 }
}
