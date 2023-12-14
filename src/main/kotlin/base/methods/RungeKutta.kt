package base.methods

import base.definitions.Method
import base.definitions.Point
import base.definitions.pointOf
import base.definitions.times

val rungeKutta: Method<Point> = { stepSize, equations ->
    var point = this.copy()
    val k1 = equations(point)
    val k2 = equations(point + k1 * stepSize / 2.0)
    val k3 = equations(this + k2 * stepSize / 2.0)
    val k4 = equations(this + k3 * stepSize)

    point += (k1 + (2.0 * k2) + (2.0 * k3) + k4) * (stepSize / 6.0)
    point[0] += stepSize
    point
}
