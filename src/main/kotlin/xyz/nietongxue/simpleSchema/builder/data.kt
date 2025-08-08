package xyz.nietongxue.simpleSchema.builder

import xyz.nietongxue.simpleSchema.ArraySchema
import xyz.nietongxue.simpleSchema.Constraint
import xyz.nietongxue.simpleSchema.DataSchema
import xyz.nietongxue.simpleSchema.NamedType
import xyz.nietongxue.simpleSchema.ObjectSchema
import xyz.nietongxue.simpleSchema.PrimitiveSchema

fun String.nameType(): PrimitiveSchema {
    return PrimitiveSchema(listOf(NamedType("string")))
}

fun PrimitiveSchema.and(other: Constraint): PrimitiveSchema {
    return PrimitiveSchema(constraints + other)
}

fun DataSchema.array(): ArraySchema {
    return ArraySchema(this)
}
fun Map<String, DataSchema>.`object`(): ObjectSchema{
    return ObjectSchema(this)
}
fun Map<String, DataSchema>.obj(): ObjectSchema{
    return ObjectSchema(this)
}