package xyz.nietongxue.simpleSchema

import xyz.nietongxue.simpleSchema.parse.RJsonFormat
import xyz.nietongxue.simpleSchema.parse.parseOperation
import kotlin.test.Test

class OperationTest() {
    @Test
    fun testOperation() {
        val rjson = """
            {
                request: string
                response: string
            }
            """
        val op = parseOperation(rjson, RJsonFormat())
        println(op)
    }

    @Test
    fun testOperation2() {
        val rjson = """
            {
                response: string
                parameters: [
                    {name: id
                    schema: string}
                ]
            }
            """
        val op = parseOperation(rjson, RJsonFormat())
        println(op)
    }
}