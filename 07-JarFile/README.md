# SpringBoot - 标题

## 疑问收集

1. springboot 可执行jar包里替换常内嵌jar后为什么会无法读取？
因为可执行jar里的内嵌jar都使用ZipEntry.STORED压缩方式(无压缩)，使用这种压缩方式是为了能记住内嵌jar的位置，从而达到目的：可以直接读取指定单个内嵌jar内容而不用将所有内嵌的jar读取到内存中。
而将外部jar使用压缩工具（360压缩工具）更新可执行jar里时，外部jar在可执行jar会使用ZipEntry.DEFLATED压缩方式。

2. 如何往可执行jar里替换内嵌jar？
使用winRAR，新增时压缩方式选择存储(STORED)，保证更新的内嵌jar是使用STORED的压缩方式。

3. 自己如何实现内嵌jar的加载？

## 重点摘要

记录学习过程觉得比较重要文字片段。

## 