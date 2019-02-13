package com.mini.living.propertyselection

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
abstract class FragmentUnitTest {

    @get:Rule
    val activityRule = ActivityTestRule<FragmentTestActivity>(FragmentTestActivity::class.java, false, false)

    @CallSuper
    @Before
    open fun setup() {
        InstrumentationRegistry.getInstrumentation().targetContext.applicationContext.setTheme(R.style.Theme_AppCompat)
        MockitoAnnotations.initMocks(this)
        activityRule.launchActivity(null)
    }

    protected fun verticalLinearManagerMock() = LinearLayoutManager(
            InstrumentationRegistry.getInstrumentation().context,
            RecyclerView.VERTICAL,
            false)
}