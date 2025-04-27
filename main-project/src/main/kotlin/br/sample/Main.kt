package br.sample


@Function(name = "functionWithoutArgs")
interface FunctionWithoutArgs

@Function(name = "myAmazingFunction")
interface MyAmazingFunction {
    val arg1: String?
    val arg2: List<Int?>?
    val arg3: List<Map<String, *>?>
}
@Function(name = "sdfsd")
data class FSD(
    val arroz: String
)

fun main() {
  
}