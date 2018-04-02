package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.di.builder;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.ui.main.MainActivity;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.ui.main.MainActivityModule;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.ui.word.WordActivity;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.ui.word.WordActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by VIVEK KUMAR SINGH on 3/27/2018.
 * <p>
 * <p>This class will use Dagger Android annotation(@ContributeAndroidInjector) which is reducing
 * boiler-plated code, and generate all AndroidInjector classes and annotations for sub-component
 * under the hood to inject the Android Framework Classes(Activities, Fragments, Services, etc).
 * <p>
 * <p>This class always contains abstract methods for Android Framework Components(Activity,
 * Fragment, etc) so this will become an abstract class
 */
@Module
public abstract class ActivityBuilder {

    /**
     * Also provide Fragment Provider inside module, if your Activity has any Fragment
     *
     * @return MainActivity (required Android Framework Classes)
     */
    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {WordActivityModule.class})
    abstract WordActivity bindWordActivity();
}
