package db

import java.sql.{Connection, DriverManager}

import config.Config
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor

/**
  * Runs Liquibase based database schema and data migrations. This is the only place for all related
  * modules to run updates from.
  *
  * Liquibase finds its files on the classpath and applies them to DB. If migration fails
  * this class will throw an exception and by default your application should not continue to run.
  *
  * It does not matter which module runs this migration first.
  */
class SchemaMigration {
  val masterChangeLogFile = Config.get("changelog.file")

  def createLiquibase(dbConnection: Connection, diffFilePath: String): Liquibase = {
    val database = DatabaseFactory.getInstance.findCorrectDatabaseImplementation(new JdbcConnection(dbConnection))
    val classLoader = classOf[SchemaMigration].getClassLoader
    val resourceAccessor = new ClassLoaderResourceAccessor(classLoader)
    new Liquibase(diffFilePath, resourceAccessor, database)
  }

  def updateDb(connection: Connection, diffFilePath: String): Unit = {
    val liquibase = createLiquibase(connection, diffFilePath)
    try {
      liquibase.update("")
    } catch {
      case e: Throwable => throw e
    } finally {
      liquibase.forceReleaseLocks()
      connection.rollback()
      connection.close()
    }
  }

  def connProvider():Connection = {
    val url = Config.get("jdbc.main.url");
    Class.forName(Config.get("jdbc.main.class"))
    val connection = DriverManager.getConnection(url)

    return connection
  }

  /**
    * Invoke this method to apply all DB migrations.
    */
  def run(): Unit = {
    val connection = connProvider()
    updateDb(connection, masterChangeLogFile)
  }

}

object SchemaMigration extends SchemaMigration