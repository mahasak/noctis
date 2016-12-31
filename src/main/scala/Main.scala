object Main {
  def main(args: Array[String]) = {
    //Run liquibase
    SchemaMigration.run()
  }
}