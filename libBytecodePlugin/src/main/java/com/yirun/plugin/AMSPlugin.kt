package com.yirun.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project


class AMSPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("asm plugin apply")
    }
}