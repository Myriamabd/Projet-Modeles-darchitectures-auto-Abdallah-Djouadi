package fr.episen.dataprocessing

import fr.episen.dataprocessing.config.Client
import org.apache.spark
import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.types.{IntegerType, LongType, StringType, StructField, StructType}

object Main {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master(master = "local").getOrCreate()
    //serviceDelete.Delete.deleteClient(sparkSession, 88)
    val dataset = serviceDelete.Delete.read(sparkSession, "data")
    serviceHachage.Hachage.hash2(sparkSession, 12, dataset)



  }



}

/*
List/ Seq / Array -> Type Class
Int/ String/ Double/ Float
Set vs List : Set = liste sans possibilité d'avoir des valeurs dupliquées
Int est l'équivalent de Set(1,2,3,....,N) (ensemble des entiers naturels
Strint <-> Set(*...*)
DataFrame = liste scalable, que l'on peut distribuer (existe en scala et python)
Dataset n'existe qu'ne scala

 */