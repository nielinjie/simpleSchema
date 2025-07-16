package xyz.nietongxue.simpleSchema.parse

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.TextNode
import xyz.nietongxue.simpleSchema.ArraySchema
import xyz.nietongxue.simpleSchema.DataSchema
import xyz.nietongxue.simpleSchema.ObjectSchema
import xyz.nietongxue.simpleSchema.PrimitiveSchema
import xyz.nietongxue.simpleSchema.json.Format
import xyz.nietongxue.simpleSchema.json.autoParse

fun parseData(json: JsonNode, format: Format): DataSchema {
    return when (json) {
        is TextNode -> PrimitiveSchema.Companion.fromString(json.textValue(), format)
            ?: error("primitiveSchema parse failed - ${json.toPrettyString()}")

        is ArrayNode -> ArraySchema(parseData(json.get(0)!!, format))
        is ObjectNode -> ObjectSchema(json.properties().associate { it.key to parseData(it.value, format) })
        else -> error("not recognized json node - ${json.toPrettyString()}")
    }
}

fun parseData(json: String, format: Format? = null): DataSchema {
    return if (format == null) autoParse(json).let {
        parseData(it.first, it.second)
    } else {
        parseData(format.json(json), format)
    }
}


