package xyz.nietongxue.simpleSchema

import com.github.erosb.jsonsKema.FormatValidationPolicy
import com.github.erosb.jsonsKema.JsonParser
import com.github.erosb.jsonsKema.JsonValue
import com.github.erosb.jsonsKema.Schema
import com.github.erosb.jsonsKema.SchemaLoader
import com.github.erosb.jsonsKema.ValidationFailure
import com.github.erosb.jsonsKema.Validator
import com.github.erosb.jsonsKema.ValidatorConfig
import xyz.nietongxue.simpleSchema.jsonSchema.toJsonSchema
import xyz.nietongxue.simpleSchema.json.RJsonFormat
import xyz.nietongxue.simpleSchema.parse.parseData
import kotlin.test.Test


class JsonSchemaTest() {
    @Test
    fun test10() {
        val rjson = """
            { computer: { pc: string/maxL(10)/minL(5)/required/notEmpty, mobile: string } ,_: string}
            """
        val dataS = parseData(rjson, RJsonFormat)
        val jsonSchema = dataS.toJsonSchema()
        println(jsonSchema.toPrettyString())
    }

    @Test
    fun test11() {
        val rjson = """
            { computer: [{ pc: string/maxL(10)/minL(5)/required/notEmpty, mobile: string }] ,_: string}
            """
        val dataS = parseData(rjson, RJsonFormat)
        val jsonSchema = dataS.toJsonSchema()
        println(jsonSchema.toPrettyString())
    }

    @Test
    fun test12() {

        // parse the schema JSON as string
        val schemaJson: JsonValue = JsonParser(
            $$"""
            {
                "$schema": "https://json-schema.org/draft/2020-12/schema",
                "type": "object",
                "properties": {
                    "age": {
                        "type": "number",
                        "minimum": 0
                    },
                    "name": {
                        "type": "string"
                    },
                    "email": {
                        "type": "string",
                        "format": "email"
                    }
                }
            }
            
            """.trimIndent()
        ).parse()

// map the raw json to a reusable Schema instance
        val schema: Schema = SchemaLoader(schemaJson).load()
// create a validator instance for each validation (one-time use object)
        val validator: Validator = Validator.create(schema, ValidatorConfig(FormatValidationPolicy.ALWAYS))
// parse the input instance to validate against the schema
        val instance: JsonValue = JsonParser(
            """
            {
                "age": -5,
                "name": null,
                "email": "invalid"
            }
            
            """.trimIndent()
        ).parse()
// run the validation
        val failure: ValidationFailure? = validator.validate(instance)
// print the validation failures (if any)
        println(failure)
    }


    @Test
    fun test2() {
        val rjson = """
            { computers: [{ pc: string/maxL(10)/minL(5)/required/notEmpty, mobile: string }] ,_: string}
            """
        val dataS = parseData(rjson, RJsonFormat)
        val jsonSchema = dataS.toJsonSchema()
        val schemaJson = jsonSchema.toPrettyString()
        val schema: Schema = SchemaLoader(schemaJson).load()
        val validator: Validator = Validator.create(schema, ValidatorConfig(FormatValidationPolicy.ALWAYS))
        val instance: JsonValue = JsonParser(
            """
            {
                "computers":[
                    {"pc": "Alice","mobile": "Bob","tablet": "Charlie"}
                ]
            }
            
            """.trimIndent()
        ).parse()
        val failure: ValidationFailure? = validator.validate(instance)
        println(failure)

    }
}