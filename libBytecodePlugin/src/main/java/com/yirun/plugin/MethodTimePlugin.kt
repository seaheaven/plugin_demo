//package com.yirun.plugin
//
//import com.android.build.api.transform.Format
//import com.android.build.api.transform.QualifiedContent
//import com.android.build.api.transform.Transform
//import com.android.build.api.transform.TransformInvocation
//import com.android.build.gradle.internal.pipeline.TransformManager
//import com.android.utils.FileUtils
//import org.objectweb.asm.ClassReader
//import org.objectweb.asm.ClassWriter
//import java.io.FileOutputStream
//
//class MethodTimePlugin : Transform() {
//    override fun getName(): String {
//        return "MethodTimePlugin"
//    }
//
//    /**
//     * 用于指明Transform的输入类型
//     */
//    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
//        return TransformManager.CONTENT_CLASS
//    }
//
//    /**
//     * 用于指明Transform的作用域
//     */
//    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
//        return TransformManager.SCOPE_FULL_PROJECT
//    }
//
//    /**
//     * 指明该Transform是否支持增量编译
//     */
//    override fun isIncremental(): Boolean {
//        return true
//    }
//
//    override fun transform(transformInvocation: TransformInvocation?) {
//        super.transform(transformInvocation)
//        val inputs = transformInvocation?.inputs
//        val outputProvider = transformInvocation?.outputProvider
//        inputs?.forEach { transformInput ->
//            // 遍历项目目录
//            transformInput.directoryInputs.forEach { directoryInput ->
//                if (directoryInput.file.isDirectory) {
//                    FileUtils.getAllFiles(directoryInput.file).forEach { file ->
//                        val name = file.name
//                        // 过滤class文件, 排除R.class, BuildConfig.class
//                        if (name.endsWith(".class") && !name.startsWith("R\$") &&
//                            name != "R.class" && name != "BuildConfig.class"
//                        ) {
//                            // 找到需要的class文件，进行插桩
//                            val path = file.absolutePath
//                            val cr = ClassReader(file.readBytes())
//                            val cw = ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
//                            val visitor = MethodTimeClassVisitor(cw)
//                            cr.accept(visitor, ClassReader.EXPAND_FRAMES)
//
//                            val bytes = cw.toByteArray()
//                            var fos: FileOutputStream? = null
//                            try {
//                                fos = FileOutputStream(path)
//                                fos.write(bytes)
//                            } catch (e: Exception) {
//                                e.printStackTrace()
//                            } finally {
//                                runCatching { fos?.close() }
//                            }
//                        }
//                    }
//                }
//                val dest = outputProvider?.getContentLocation(
//                    directoryInput.name,
//                    directoryInput.contentTypes,
//                    directoryInput.scopes,
//                    Format.DIRECTORY
//                )
//                FileUtils.copyDirectoryToDirectory(directoryInput.file, dest)
//            }
//
//            // 遍历jar包
//            transformInput.jarInputs.forEach { jarInput ->
//                val dest = outputProvider?.getContentLocation(
//                    jarInput.name,
//                    jarInput.contentTypes,
//                    jarInput.scopes,
//                    Format.JAR
//                )
//                FileUtils.copyFile(jarInput.file, dest)
//            }
//        }
//    }
//}