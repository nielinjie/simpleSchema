package xyz.nietongxue.simple_schema

import xyz.nietongxue.simple_schema.RJson.rjsonToJackson
import kotlin.test.Test



/*
声明样式：
1. string/number/boolean
2. [string]
2. { propertyA: string }
2. { propertyA: { pc: string, mobile: string } }
3. { _: any }
3. { propertyA: [string] }
4. { propertyA: [string],propertyB: number }
5. { propertyA: [string], propertyB: {pc: ....}}
6. string/max(5)/notBlank....
7. string/default(....) // 比较难，往后排。💪。
 */

class RJsonTest() {


    @Test
    fun test() {
        val rjson = """
                {foo:bar}
            """
        val parse = RJson.parse(rjson)
        val json = rjsonToJackson(parse)
        println(json.toPrettyString())
    }
    @Test
    fun test2() {
        val rjson = """
            [{_:string}]
            """
        val parse = RJson.parse(rjson)
        val json = rjsonToJackson(parse)
        println(json.toPrettyString())
    }

    @Test
    fun test3() {
        val rjson = """
            { propertyA: { pc: string, mobile: string } }
            """
        val parse = RJson.parse(rjson)
        val json = rjsonToJackson(parse)
        println(json.toPrettyString())
    }
    @Test
    fun test4() {
        val rjson = """
            { propertyA: { pc: string/max(10)/min(5)/required, mobile: string } }
            """
        val parse = RJson.parse(rjson)
        val json = rjsonToJackson(parse)
        println(json.toPrettyString())
    }
    @Test
    fun test10(){
        val rjson = """
            { propertyA: { pc: string/maxL(10)/minL(5)/required/notEmpty, mobile: string } }
            """
        val dataS = parse(rjson, RJsonFormat())
        println(dataS)
    }
}