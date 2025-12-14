package com.example.mobileproject


import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.compose.ui.test.*


@RunWith(AndroidJUnit4::class)
class TaskListEspressoTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addTask_displaysInList() {
        val title = "Espresso Test"
        val description = "This is a test task"

        composeTestRule.onNodeWithText("Add Task").performClick()

        composeTestRule.onNodeWithText("Task Title").performTextInput(title)
        composeTestRule.onNodeWithText("Task Description").performTextInput(description)

        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
        composeTestRule.onNodeWithText("Pending").assertIsDisplayed()
    }

    @Test
    fun deleteTask_removesFromList() {
        val title = "Task to Delete"

        composeTestRule.onNodeWithText("Add Task").performClick()
        composeTestRule.onNodeWithText("Task Title").performTextInput(title)
        composeTestRule.onNodeWithText("Task Description").performTextInput("Desc")
        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.onNodeWithText(title).assertIsDisplayed()

        composeTestRule.onAllNodes(hasContentDescription("Delete Task"))[0].performClick()

        composeTestRule.onNodeWithText(title).assertDoesNotExist()
    }
}
