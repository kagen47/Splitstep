package com.genenakagaki.splitstep.exercise.ui.coach;

import android.view.View;

import com.genenakagaki.splitstep.R;
import com.genenakagaki.splitstep.exercise.ui.model.DurationDisplayable;

import butterknife.OnClick;

/**
 * Created by Gene on 9/13/2017.
 */

public class RegularCoachFragment extends CoachFragment {

    public static CoachFragment newInstance(long exerciseId) {
        return CoachFragment.newInstance(exerciseId, new RegularCoachFragment());
    }

    private TimerViewModel mTimedSetsTimerViewModel;

    @Override
    public void setupExerciseSet() {
        switch (getViewModel().getExerciseSubType()) {
            case REPS:
                int reps = getViewModel().getExercise().reps;
                mMainProgressTopText.setText(getString(R.string.reps_count, reps));
                break;
            case TIMED_SETS:
                int setDuration = getViewModel().getExercise().setDuration;

                mTimedSetsTimerViewModel = new TimerViewModel(
                        new DurationDisplayable(DurationDisplayable.TYPE_SET_DURATION, setDuration));
                mMainProgressBar.setMax(mTimedSetsTimerViewModel.getMax());
                break;
        }
    }

    @Override
    public void startExerciseSet() {
        switch (getViewModel().getExerciseSubType()) {
            case REPS:
                mDoneButton.setVisibility(View.VISIBLE);
                break;
            case TIMED_SETS:
                mMainProgressBar.setVisibility(View.VISIBLE);
//                mMainProgressBar.setStartProgress(mTimedSetsTimerViewModel.getMax());
                animateProgress(mMainProgressBar, 0, mTimedSetsTimerViewModel.getAnimateDuration());
                mMainProgressText.setText(mTimedSetsTimerViewModel.getTimerDisplay());

                addDisposable(mTimedSetsTimerViewModel.startTimer().subscribe(
                        s -> mMainProgressText.setText(s),
                        throwable -> {},
                        () -> {
                            mMainProgressBar.setVisibility(View.INVISIBLE);
                            onFinishExerciseSet();
                        }));
                break;
        }
    }

    @OnClick(R.id.done_button)
    public void onClickDone() {
        mDoneButton.setVisibility(View.INVISIBLE);
        onFinishExerciseSet();
    }
}
