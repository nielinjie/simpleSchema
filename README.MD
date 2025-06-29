# simple schema

以简单的方式定义一个数据结构。

不一定一严密，追求一般够用。

适合用在配置文件等人类手写和阅读的场合。

### 约定 

1. `string/number/boolean`  //字符串/数字/布尔
2. `[string]` - 数组 array
2. `{ propertyA: string }` - 简单 object
2. `{ propertyA: { pc: string, mobile: string } }` - 嵌套 object
3. `{ _: any }` - additionalProperties
3. `{ propertyA: [string] }` - 数组类属性
4. `{ propertyA: [string],propertyB: number }` - 多个属性
5. `{ propertyA: [string], propertyB: {pc: ....}}` - 嵌套 object
6. `string/max(5)/notBlank....` - 校验规则
7. `string/default(....)` // TODO 比较难，往后排。default 是一个转换，而不是一个校验


### 例子 

```
 { computers: [{ pc: string/maxL(10)/minL(5)/required/notEmpty, mobile: string }] ,_: string}

```

### 使用

1. 直接使用 DataSchema 类型。
2. 转换为其他成熟的 schema 定义来使用。比如 jsonSchema，sql……
   1. testcase 中有个将 simple schema 转换为标准 jsonSchema 并用于验证的演示。

### WIP

1. 校验规则较复杂的一些。
2. default。