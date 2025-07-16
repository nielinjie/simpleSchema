package xyz.nietongxue.simpleSchema

import org.assertj.core.api.Assertions.assertThat
import xyz.nietongxue.simpleSchema.json.NormalJson
import xyz.nietongxue.simpleSchema.parse.parseData
import kotlin.test.Test
import kotlin.test.fail


class ParseTest() {
    @Test
    fun test() {
        val jsonString = """
            "string"
        """.trimIndent()
        val parsed = schema(jsonString)
        assertThat(parsed).isInstanceOf(PrimitiveSchema::class.java)
    }

    @Test
    fun test2() {
        val jsonString = """
            ["string"]
            """
        val parsed = schema(jsonString)
        require(parsed is ArraySchema)
        assert(parsed.itemSchema is PrimitiveSchema)
    }

    @Test
    fun test3() {
        val jsonString = """
            {
                "name": "string"
            }
            """
        val parsed = schema(jsonString)
        require(parsed is ObjectSchema)
        assert(parsed.properties["name"] is PrimitiveSchema)
    }

    @Test
    fun test4() {
        val jsonString = """
                {
                "name": "string",
                "house":[
                   { "address":"string"}
                ]
            }
            """
        val parsed = schema(jsonString)
        require(parsed is ObjectSchema)
        parsed.properties["house"]?.also {
            require(it is ArraySchema)
            assert(it.itemSchema is ObjectSchema)
        } ?: fail("house not found")

    }

    @Test
    fun test5() {
        val jsonString = """
                {
                "name": "string",
                "house":[
                   { "address":"string",
                   "_":"string"}
                ]
            }
            """
        val parsed = schema(jsonString)
        require(parsed is ObjectSchema)
        parsed.properties["house"]?.also {
            require(it is ArraySchema)
            require(it.itemSchema is ObjectSchema)
            val additional = it.itemSchema.additional()
            (additional as AdditionalProperties).also {
                assert(it.schema is PrimitiveSchema)
            }
        } ?: fail("house not found")

    }

    private fun schema(jsonString: String): DataSchema {
        val format = NormalJson
        val json = format.json(jsonString)
        val parsed = parseData(json, format)
        return parsed
    }
}