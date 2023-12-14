package base.solvers

import base.definitions.Limit
import base.definitions.Method
import base.definitions.Point
import base.definitions.Solver
import base.methods.rungeKutta

class RungeKuttaSolver(
    val initialValues: Point,
    override val limit: Limit<Point>,
    override val stepSize: Double,
    override val differential: (Point) -> Point,
) : Solver<Point> {
    override var currentStepPoint: Point = initialValues
    override val method: Method<Point> = rungeKutta
    var i = 0
    override fun preSolve() {

    }

    override fun postSolve() {
        println("Solving the differential equations has ended. Number of steps taken $i")
    }

    override fun preStep() {
        println("step: $i --")
        for (i in 0..<currentStepPoint.dimension) {
            print("-- point[$i] = ${currentStepPoint[i]}")
        }
    }

    override fun postStep() {
        i++
    }
}
