# language: zh-CN

功能: 书籍价格查询接口
  作为一个顾客
  我想要知道一本书籍的按照汇率换算过的价格
  以便决定是否需要购买

  场景: 人民币客户
    当 我查询书名"Kotlin实战"的"cny"价格
    那么 我看到的价格应该是"89.00"

  场景: 美元客户
    假如 当天的"cny"与"usd"汇率是0.15168
    当 我查询书名"Kotlin实战"的"usd"价格
    那么 我看到的价格应该是"13.50"


