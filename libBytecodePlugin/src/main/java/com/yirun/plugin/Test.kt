package com.yirun.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project


class Test: Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create("asmconfig", AsmExtension::class.java)

        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.onVariants { variant ->
            val extension = project.extensions.getByType(AsmExtension::class.java)
            println("AsmPlugin ${extension.specificClass}")
            variant.instrumentation.setAsmFramesComputationMode(
                FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS
            )
            variant.instrumentation.transformClassesWith(
                TestClassVistorFactory::class.java,
                InstrumentationScope.PROJECT

            ) { params ->
                params.specificClass.set(extension.specificClass)
            }
        }


    }

}