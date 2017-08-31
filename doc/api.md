# 书籍价格查询API

## 默认查询人民币
GET
price?name=Kotlin实战

```json
{
  "name":"Kotlin实战",
  "price":"89.0",
  "currency":"cny"
}
```
## 查询其他币种
GET
price?name=Kotlin实战&currency=usd


```json
{
  "name":"Kotlin实战",
  "price":"13.50",
  "currency":"usd"
}
```


