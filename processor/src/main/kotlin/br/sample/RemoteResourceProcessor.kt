@file:Suppress("UnnecessaryVariable")

package br.sample

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import java.io.OutputStream


/**
 * This processor handles interfaces annotated with @Function.
 * It generates the function for each annotated interface. For each property of the interface it adds an argument for
 * the generated function with the same type and name.
 *
 * For example, the following code:
 *
 * ```kotlin
 * @Function(name = "myFunction")
 * interface MyFunction {
 *     val arg1: String
 *     val arg2: List<List<*>>
 * }
 * ```
 *
 * Will generate the corresponding function:
 *
 * ```kotlin
 * fun myFunction(
 *     arg1: String,
 *     arg2: List<List<*>>
 * ) {
 *     println("Hello from myFunction")
 * }
 * ```
 */
class RemoteResourceProcessor(
    private val options: Map<String, String>,
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {

    operator fun OutputStream.plusAssign(str: String) {
        this.write(str.toByteArray())
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver
            // Getting all symbols that are annotated with @RemoteResource.
            .getSymbolsWithAnnotation("br.sample.RemoteResource")
            .filterIsInstance<KSPropertyDeclaration>()

        symbols.forEach { it.accept(Visitor(), Unit) }

        val unableToProcess = symbols.filterNot { it.validate() }.toList()
        return unableToProcess
    }

    inner class Visitor() : KSVisitorVoid() {

        override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
            // Generating argument name
            val type = property.type.resolve()

            val isStringType = type.declaration.qualifiedName?.asString() == "kotlin.String"
            if (isStringType.not())
                logger.error("Only String type is supported for @RemoteResource Annotation", property)
        }
    }
}