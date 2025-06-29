package xyz.nietongxue.simple_schema

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import xyz.nietongxue.common.base.cList
import xyz.nietongxue.simple_schema.RJson.rjsonToJackson


interface Format {
    fun json(string: String): JsonNode
    fun shortList(string: String): List<String> {
        return string.split(",")
    }
}


/*
声明样式：
1. string/number/boolean
2. [string]
2. { "propertyA": string }
2. { "propertyA": { "pc": string, "mobile": string } }
3. { "_": string }
3. { "propertyA": [string] }
4. { "propertyA": [string], "propertyB": number }
5. { "propertyA": [string], "propertyB": {"pc": ....}}
6. string/max(5)/notBlank
7. string/default(xxx) TODO 比较难，往后排。default 是一个转换，而不是一个校验
 */

/*
https://www.relaxedjson.org
 */
class RJsonFormat() : Format {
    override fun json(string: String): JsonNode {
        return rjsonToJackson(RJson.parse(string))
    }

    override fun shortList(string: String): List<String> {
        return string.cList()
    }

}

/*
json
就是一般的 json。
 */

class Json() : Format {
    override fun json(string: String): JsonNode {
        return jacksonObjectMapper().readTree(string)
    }

}