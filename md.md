1. Objects.toString(obj, 默认字符串)
将对象转换为字符串。这个方法接受两个参数：
第一个参数是要转换的对象，这里是env.getProperty("server.port")的返回值，它可能是一个String、null或者任何其他类型的对象。
第二个参数是一个默认字符串，如果第一个参数为null，toString方法将返回这个默认字符串。在这个例子中，默认字符串是""（一个空字符串）