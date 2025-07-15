package xyz.nietongxue.simpleSchema.parse

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import xyz.nietongxue.simpleSchema.Operation
import xyz.nietongxue.simpleSchema.Parameter

fun parseOperation(json: String, format: Format): Operation {
    val json = format.json(json) as ObjectNode
    var op = Operation()
    json.properties().forEach { (key, value) ->
        when (key) {
            "request" -> {
                op = op.copy(request = parseData(value, format))
            }

            "response" -> {
                op = op.copy(response = parseData(value, format))
            }

            "parameters" -> {
                val parameters: List<Parameter> = (value as ArrayNode).map {
                    jsonToParameter(it as ObjectNode, format)
                }
                op = op.copy(parameters = parameters)
            }
        }
    }
    return op
}

fun jsonToParameter(json: ObjectNode, format: Format): Parameter {
    val om = jacksonObjectMapper()

    val schema = json.get("schema")?.let {
        parseData(it, format)
    } ?: error("no schema find")
    val name = json.get("name")?.textValue() ?: error("no name find")
    val required = json.get("required")?.booleanValue() ?: false
    val style = json.get("style")?.textValue() ?: "form"
    return Parameter(name, schema, required, style)
}
