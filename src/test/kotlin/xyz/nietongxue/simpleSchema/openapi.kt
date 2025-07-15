package xyz.nietongxue.simpleSchema

import xyz.nietongxue.simpleSchema.openApi.toOpenApi
import xyz.nietongxue.simpleSchema.parse.RJsonFormat
import xyz.nietongxue.simpleSchema.parse.parseData
import xyz.nietongxue.simpleSchema.parse.parseOperation
import kotlin.test.Test

class OpenApiTest() {
    @Test
    fun testSchema() {
        val rjson = """
            { computers: [{ pc: string/maxL(10)/minL(5)/required/notEmpty, mobile: string }] ,_: string}
            """
        val dataS = parseData(rjson, RJsonFormat())
        dataS.toOpenApi()
            .also {
                println(it) //NOTE schema自己的 toString 不靠谱。
            }
    }

    @Test
    fun testOperation() {
        val rjson = """
            {
                response: string
                parameters: [
                    {name: id
                    schema: string}
                ]
            }
            """
        val operation = parseOperation(rjson, RJsonFormat())
        operation.toOpenApi().also {
            println(it)
        }
    }
}