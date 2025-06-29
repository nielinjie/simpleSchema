package xyz.nietongxue.simple_schema

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.TextNode

fun parse(json: JsonNode, format: Format): DataSchema {
    return when (json) {
        is TextNode -> PrimitiveSchema.fromString(json.textValue(), format)
            ?: error("primitiveSchema parse failed - ${json.toPrettyString()}")

        is ArrayNode -> ArraySchema(parse(json.get(0)!!, format))
        is ObjectNode -> ObjectSchema(json.properties().associate { it.key to parse(it.value, format) })
        else -> error("not recognized json node - ${json.toPrettyString()}")
    }
}

fun parse(json: String, format: Format): DataSchema {
    return parse(format.json(json), format)
}