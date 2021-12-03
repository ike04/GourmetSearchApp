package com.google.codelab.gourmetsearchapp.view

enum class MainBottomNavigationSelectedItem {
    HOME,
    MAP,
    SETTINGS;

    fun isHome(): Boolean = (this == HOME)
    fun isMap(): Boolean = (this == MAP)
    fun isSettings(): Boolean = (this == SETTINGS)
}
