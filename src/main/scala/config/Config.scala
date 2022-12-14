package fr.episen.dataprocessing
package config

import scopt.OParser



object Config {
  case class Config(
                     IdentifiantClient: Int = -1,
                     filepath: String = " ",
                     finalpath: String = " ",
                     service: String = " "
                   )
  val builder = OParser.builder[Config]
  val argParser = {
    import builder._
    OParser.sequence(
      programName("spark1"),
      head("Client", "0.1"),
      opt[Int]('i', " IdentifiantClient")
        .required()
        .action((i, c) => c.copy( IdentifiantClient = i))
        .text("required integer"),
      opt[String]('o', "filepath")
        .required()
        .action((o, c) => c.copy( filepath = o))
        .text("required string"),
      opt[String]('f', "finalpath")
        //.required()
        .action((f, c) => c.copy(finalpath = f))
        .text("required string"),
      opt[String]('s', "service")
        .required()
        .action((s, c) => c.copy(service = s))
        .text("required string"))
      .validate(s => {
        if (s == "deleteClient" || s == "hashedData") {
          success
        } else {
          failure("service required are deleteClient or hashedData")
        }
      })
  }
}
