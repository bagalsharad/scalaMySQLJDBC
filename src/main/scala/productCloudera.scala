/**
  * Created by sharadbagal on 4/2/17.
  */
import java.sql.DriverManager
import java.sql.Connection

case class Products(product_name: String ) {

  override def toString(): String = {
    s"product_name: " + product_name +";"
  }

}


object productCloudera {
  def main(argus: Array[String]): Unit ={

    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://172.16.206.148:3306/retail_db"
    val username = "retail_dba"
    val password = "cloudera"


      Class.forName(driver);
      val connection = DriverManager.getConnection(url,username,password)
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery(s"Select product_name From products")

      //val columnVal  = resultSet.first()
    //println(columnVal)

      while (resultSet.next()){
        val e = "Product Name : " + resultSet.getString("product_name")
      println(e)
      }

  }
}
