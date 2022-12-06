package fr.episen.dataprocessing
package serviceHachage

import fr.episen.dataprocessing.config.Client
import fr.episen.dataprocessing.serviceDelete.Delete.write
import org.apache.spark
import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.functions.{col, md5, sha2, udf, when}
import org.apache.spark.sql.types.StringType
import org.apache.spark.util.Utils

import java.security.MessageDigest

object Hachage {


  def hash2 (sparkSession: SparkSession, id: Long, dataset : Dataset[Client]): Unit = {
    val dataset = serviceDelete.Delete.read(sparkSession, "data")
    val filteredID = dataset.filter(col("IdentifiantClient") === id)
    val hashedColumn = udf((input: String) => MessageDigest.getInstance("SHA-256").digest(input.getBytes).map("%02x".format(_)).mkString)
    val hashedData = filteredID.withColumn("Nom", hashedColumn(col("Nom"))).withColumn("Prenom", hashedColumn(col("Prenom"))).withColumn("Adresse", hashedColumn(col("Adresse")))
    hashedData.write.csv("data/hash")
    hashedData.show()
  }

  /*def hashClient(sparkSession: SparkSession, id: Long): Dataset[Client] = {
    val dataset = serviceDelete.Delete.read(sparkSession, "data")
    val hashData = hash2(id, dataset)
    hashData.show()
    serviceDelete.Delete.write(hashData, "data/hash")
    hashData
  }*/


}




