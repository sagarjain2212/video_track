package com.youtubetrack

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.youtubetrack.screen.ProductScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import youtubetrack.HiltTestActivity

@HiltAndroidTest
class ProductScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Before
    fun init() {
        hiltRule.inject()
        composeTestRule.setContent {
            // MaterialTheme wrap is recommended if your composables use Material components
            ProductScreen()
        }
    }

    @Test
    fun floatingActionButton_click_opensDialog() {
        // Find FAB by content description and click
        composeTestRule.onNodeWithContentDescription("Add").performClick()

        // Verify Dialog title is displayed
        composeTestRule.onNodeWithText("Add New Product").assertIsDisplayed()
    }

    @Test
    fun addProductDialog_inputAndSave_dismissesDialog() {
        // 1. Open Dialog
        composeTestRule.onNodeWithContentDescription("Add").performClick()

        // 2. Input Title
        composeTestRule.onNodeWithText("Product Title")
            .performTextInput("My New Phone")

        // 3. Open Dropdown and select category
        composeTestRule.onNodeWithText("Category").performClick()
        composeTestRule.onNodeWithText("Test2").performClick()

        // 4. Click Save
        composeTestRule.onNodeWithText("Save").performClick()

        // 5. Verify dialog is gone
        composeTestRule.onNodeWithText("Add New Product").assertDoesNotExist()
    }
}