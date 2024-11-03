package com.group6.hms.framework.screens.option;

import com.group6.hms.framework.screens.ConsoleColor;

/**
 * Represents an option in a console interface.
 * This class is used to store information about a selectable option in a console-based user interface.
 *
 * @param optionId            The unique identifier for the option.
 * @param optionDescription   A brief description of what selecting this option will do.
 * @param color               The color that should be used to display this option in the console.
 */
public record Option(int optionId, String optionDescription, ConsoleColor color) {
}
