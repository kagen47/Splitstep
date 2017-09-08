package com.genenakagaki.splitstep.exercise.ui.list;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.genenakagaki.splitstep.R;
import com.genenakagaki.splitstep.exercise.data.ExerciseDatabase;
import com.genenakagaki.splitstep.exercise.data.entity.Exercise;
import com.genenakagaki.splitstep.exercise.data.entity.ExerciseType;
import com.genenakagaki.splitstep.exercise.utils.DatabaseUtils;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;

import static org.junit.Assert.assertEquals;

/**
 * Created by Gene on 9/8/2017.
 */
public class ExerciseListViewModelTest {

    private static final Exercise EXERCISE = new Exercise(1, "test", false);

    private Context mContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void setUp() throws Exception {
        FlowManager.init(FlowConfig.builder(mContext)
                .addDatabaseConfig(DatabaseConfig.inMemoryBuilder(ExerciseDatabase.class)
                        .databaseName("ExerciseDatabase")
                        .build())
                .build());
    }

    @After
    public void tearDown() throws Exception {
        FlowManager.destroy();
    }

    @Test
    public void testGetTitle_WithRepsExercise_ShouldReturnCorrectTitle() {
        ExerciseListViewModel viewModel = new ExerciseListViewModel(mContext, ExerciseType.REPS);

        String title = viewModel.getTitle();
        String expectedTitle = mContext.getString(R.string.reps_exercise);

        assertEquals(expectedTitle, title);
    }

    @Test
    public void testGetTitle_WithTimedSetsExercise_ShouldReturnCorrectTitle() {
        ExerciseListViewModel viewModel = new ExerciseListViewModel(mContext, ExerciseType.TIMED_SETS);

        String title = viewModel.getTitle();
        String expectedTitle = mContext.getString(R.string.timed_sets_exercise);

        assertEquals(expectedTitle, title);
    }

    @Test
    public void testGetTitle_WithReactionExercise_ShouldReturnCorrectTitle() {
        ExerciseListViewModel viewModel = new ExerciseListViewModel(mContext, ExerciseType.REACTION);

        String title = viewModel.getTitle();
        String expectedTitle = mContext.getString(R.string.reaction_exercise);

        assertEquals(expectedTitle, title);
    }

    @Test
    public void testGetExerciseList_WithNoExercise_SubjectShouldEmitEmptyList() {
        ExerciseListViewModel viewModel = new ExerciseListViewModel(mContext, ExerciseType.REPS);

        viewModel.getExerciseList()
                .test()
                .awaitTerminalEvent();

        viewModel.getExercisesSubject()
                .test()
                .assertValue(new Predicate<List<Exercise>>() {
                    @Override
                    public boolean test(@NonNull List<Exercise> exercises) throws Exception {
                        return exercises.size() == 0;
                    }
                });
    }

    @Test
    public void testGetExerciseList_WithRepsExercise_SubjectShouldEmitRepsExercises() {
        ExerciseListViewModel viewModel = new ExerciseListViewModel(mContext, ExerciseType.REPS);

        String[] repsExerciseNames = {
                "repsExercise1",
                "repsExercise2",
                "repsExercise3"
        };

        DatabaseUtils.insertExercises(repsExerciseNames, ExerciseType.REPS_VALUE, false);

        viewModel.getExerciseList()
                .test()
                .awaitTerminalEvent();

        viewModel.getExercisesSubject()
                .test()
                .assertValue(new Predicate<List<Exercise>>() {
                    @Override
                    public boolean test(@NonNull List<Exercise> exercises) throws Exception {
                        return exercises.size() == 3;
                    }
                });
    }

    @Test
    public void testGetExerciseList_WithTimedSetsExercise_SubjectShouldEmitTimedSetsExercises() {
        ExerciseListViewModel viewModel = new ExerciseListViewModel(mContext, ExerciseType.TIMED_SETS);

        String[] timedSetsExerciseNames = {
                "timedSetsExercise1",
                "timedSetsExercise2",
                "timedSetsExercise3"
        };

        DatabaseUtils.insertExercises(timedSetsExerciseNames, ExerciseType.TIMED_SETS_VALUE, false);

        viewModel.getExerciseList()
                .test()
                .awaitTerminalEvent();

        viewModel.getExercisesSubject()
                .test()
                .assertValue(new Predicate<List<Exercise>>() {
                    @Override
                    public boolean test(@NonNull List<Exercise> exercises) throws Exception {
                        return exercises.size() == 3;
                    }
                });
    }

    @Test
    public void testGetExerciseList_WithReactionExercise_SubjectShouldEmitReactionExercises() {
        ExerciseListViewModel viewModel = new ExerciseListViewModel(mContext, ExerciseType.REACTION);

        String[] reactionExerciseNames = {
                "reactionExercise1",
                "reactionExercise2",
                "reactionExercise3"
        };

        DatabaseUtils.insertExercises(reactionExerciseNames, ExerciseType.REACTION_VALUE, false);

        viewModel.getExerciseList()
                .test()
                .awaitTerminalEvent();

        viewModel.getExercisesSubject()
                .test()
                .assertValue(new Predicate<List<Exercise>>() {
                    @Override
                    public boolean test(@NonNull List<Exercise> exercises) throws Exception {
                        return exercises.size() == 3;
                    }
                });
    }
}