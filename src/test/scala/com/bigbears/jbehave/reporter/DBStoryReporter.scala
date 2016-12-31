package com.bigbears.jbehave.reporter

import java.util

import com.typesafe.scalalogging.LazyLogging
import org.jbehave.core.model.{ExamplesTable, Meta, OutcomesTable, StoryDuration, _}
import org.jbehave.core.reporters.StoryReporter

class DBStoryReporter extends StoryReporter with LazyLogging {
  override def pending(step: String): Unit = {
    logger.info("pending: " + step)
  }

  override def notPerformed(step: String): Unit = {
    logger.info("notPerformed: " + step)
  }

  override def afterScenario(): Unit = {
    logger.info("afterScenario")
  }

  override def comment(step: String): Unit = {
    logger.info("comment: " + step)
  }

  override def storyCancelled(story: Story, storyDuration: StoryDuration): Unit = {
    logger.info("storyCancelled: " + story + ", storyDuration: " + storyDuration)
  }

  override def beforeScenario(scenarioTitle: String): Unit = {
    logger.info("scenarioTitle: " + scenarioTitle)
  }

  override def narrative(narrative: Narrative): Unit = {
    logger.info("narrative: " + narrative)
  }

  override def ignorable(step: String): Unit = {
    logger.info("ignorable: " + step)
  }

  override def storyNotAllowed(story: Story, filter: String): Unit    = {
    logger.info("storyNotAllowed: " + story + ", filter: " + filter)
  }

  override def afterExamples(): Unit  = {
    logger.info("afterExamples")
  }


  override def dryRun(): Unit  = {
    logger.info("dryRun")
  }

  override def beforeStep(step: String): Unit = {
    logger.info("beforeStep: " + step)
  }

  override def afterStory(givenOrRestartingStory: Boolean): Unit = {
    logger.info("afterStory: " + givenOrRestartingStory)
  }

  override def failedOutcomes(step: String, table: OutcomesTable): Unit = {
    logger.info("failedOutcomes: " + step + ", table: " + table)
  }

  override def givenStories(givenStories: GivenStories): Unit = {
    logger.info("givenStories: " + givenStories)
  }

  override def givenStories(storyPaths: util.List[String]): Unit = {
    logger.info("givenStories: " + storyPaths)
  }

  override def restartedStory(story: Story, cause: Throwable): Unit  = {
    logger.info("restartedStory: " + story + ", cause:" + cause)
  }

  override def beforeStory(story: Story, givenStory: Boolean): Unit = {
    logger.info("BeforeStory: " + story)
  }

  override def lifecyle(lifecycle: Lifecycle): Unit = {
    logger.info("lifecyle: " + lifecycle)
  }

  override def pendingMethods(methods: util.List[String]): Unit = {
    logger.info("pendingMethods: " + methods)
  }

  override def scenarioNotAllowed(scenario: Scenario, filter: String): Unit = {
    logger.info("scenarioNotAllowed: " + scenario + ", " + filter)
  }

  override def scenarioMeta(meta: Meta): Unit = {
    logger.info("scenarioMeta: " + meta)
  }

  override def beforeExamples(steps: util.List[String], table: ExamplesTable): Unit = {
    logger.info("beforeExamples: " + steps + ", table: " + table)
  }

  override def example(tableRow: util.Map[String, String]): Unit = {
    logger.info("example: " + tableRow)
  }

  override def successful(step: String): Unit = {
    logger.info("successful: " + step)
  }

  override def restarted(step: String, cause: Throwable): Unit = {
    logger.info("restarted: " + step + ", cause: " + cause)
  }

  override def failed(step: String, cause: Throwable): Unit =  {
    logger.info("failed: " + step + ", cause: " + cause)
  }
}
