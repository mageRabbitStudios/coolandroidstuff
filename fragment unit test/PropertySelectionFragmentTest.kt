package com.mini.living.propertyselection.view

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.mini.living.base.router.AppFragmentFlowRouter
import com.mini.living.propertyselection.FragmentUnitTest
import com.mini.living.propertyselection.PropertySelectiontestValues.PROPERTY_LIST
import com.mini.living.propertyselection.R
import com.mini.living.propertyselection.view.recycler.PropertyListAdapter
import com.mini.living.propertyselection.viewmodel.PropertySelectionViewModel
import com.mini.living.propertyselection.viewmodel.PropertySelectionViewModel.PropertySelectionState.Loading
import com.mini.living.propertyselection.viewmodel.PropertySelectionViewModel.PropertySelectionState.PropertiesLoaded
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock

class PropertySelectionFragmentTest : FragmentUnitTest() {

    private fun injectMocks(fragment: PropertySelectionFragment) {
        fragment.apply {
            propertyListAdapter = mockPropertyListAdapter
            appFragmentFlowRouter = mockAppFragmentFlowRouter
            propertySelectionViewModel = mockViewModel
            linearLayoutManager = verticalLinearManagerMock()
        }

        given(fragment.propertySelectionViewModel.state).willReturn(mockState)
    }

    @Mock
    private lateinit var mockPropertyListAdapter: PropertyListAdapter

    @Mock
    private lateinit var mockAppFragmentFlowRouter: AppFragmentFlowRouter

    @Mock
    private lateinit var mockViewModel: PropertySelectionViewModel

    private var mockState = MutableLiveData<PropertySelectionViewModel.PropertySelectionState>()

    override fun setup() {
        super.setup()
        activityRule.activity.startFragment(PropertySelectionFragment(), ::injectMocks)
    }

    @Test
    fun `handleState() - VM's state is set to Loading`() {

        mockState.value = Loading

        onView(withId(R.id.select_property_title)).check(matches(not(isDisplayed())))
        onView(withId(R.id.select_property_icon)).check(matches(not(isDisplayed())))
    }

    @Test
    fun `handleState() - VM's state is set to PropertiesLoaded`() {

        mockState.value = PropertiesLoaded(PROPERTY_LIST)

        onView(withId(R.id.select_property_icon)).check(matches(isDisplayed()))
    }
}
