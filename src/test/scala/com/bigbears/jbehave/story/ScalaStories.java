package com.bigbears.jbehave.story;

/**
 * Created by mahasak on 12/31/2016 AD.
 */

import java.util.List;
import com.bigbears.jbehave.step.ScalaSteps;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.configuration.scala.ScalaContext;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.scala.ScalaStepsFactory;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.ANSI_CONSOLE;

public class ScalaStories extends JUnitStories {

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .useStoryReporterBuilder(new StoryReporterBuilder().withFormats(ANSI_CONSOLE));
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder()
                .findPaths(codeLocationFromClass(this.getClass()), "**/*.story", "");
    }
    /*
    @Override
    public InjectableStepsFactory stepsFactory() {
        return new ScalaStepsFactory(configuration(), new ScalaContext("ScalaSteps"));
    }
    */

    public List<CandidateSteps> candidateStepses() {
        return new InstanceStepsFactory(configuration(),new ScalaSteps()).createCandidateSteps();
    }

}