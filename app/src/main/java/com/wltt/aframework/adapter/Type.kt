package com.wltt.aframework.adapter

class Type<T> {

    var className: String = ""


    constructor(clazz: Class<T>) {
        if (clazz.name == "kotlin.jvm.internal.StringCompanionObject") {
            className = "java.lang.String"
        } else if (clazz.name == "kotlin.jvm.internal.ShortCompanionObject") {
            className = "java.lang.Short"
        } else if (clazz.name == "kotlin.jvm.internal.IntCompanionObject") {
            className = "java.lang.Integer"
        } else if (clazz.name == "kotlin.jvm.internal.LongCompanionObject") {
            className = "java.lang.Long"
        } else if (clazz.name == "kotlin.jvm.internal.DoubleCompanionObject") {
            className = "java.lang.Double"
        } else if (clazz.name == "kotlin.jvm.internal.FloatCompanionObject") {
            className = "java.lang.Float"
        } else if (clazz.name == "kotlin.jvm.internal.BooleanCompanionObject") {
            className = "java.lang.Boolean"
        } else {
            className = clazz.name
        }
    }


}
