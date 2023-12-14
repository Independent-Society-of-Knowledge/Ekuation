package base.definitions

typealias Method<T> = T.(stepSize: Double, equations: (T) -> T) -> T
