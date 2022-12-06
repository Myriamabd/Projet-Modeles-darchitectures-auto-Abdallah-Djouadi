package fr.episen.dataprocessing
package serviceDelete

import config.Client

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.types.{IntegerType, LongType, StringType, StructField, StructType}

object Delete {

    def read( sparkSession: SparkSession, path : String): Dataset[Client] = {
      import sparkSession.implicits._
      val data = sparkSession.read
        .option("header", true)
        .option("delimiter", ";")
        .csv(path).coalesce(1)
        .withColumn("IdentifiantClient", 'IdentifiantClient.cast(LongType))
        .as[Client]

      data.show()
      data
    }

    def write (dataset : Dataset[Client], path : String): Unit ={
      dataset.write
        .option("header", true)
        .option("delimiter", ";")
        .mode("overwrite")
        .csv(path)

      import scala.sys.process._
      //s"hdfs dfs -mv new data" !
    }

    def delete(dataset: Dataset[Client], id: Long): Dataset[Client] = {
      val dataset_delete = dataset.filter(!col("IdentifiantClient").isin(id))
      dataset_delete
    }

    def deleteClient(sparkSession: SparkSession, id: Long): Dataset[Client] ={
      val Path = "hdfs://172.31.254.20:9090/user/bronze/json/data"
      val dataset = read(sparkSession, Path)
      dataset.show()
      val new_dataset = delete(dataset, id)
      new_dataset.show()
      write(new_dataset, Path +"/new")
      new_dataset
    }

}

