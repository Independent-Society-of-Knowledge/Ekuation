package base.definitions


/**
 * Solver interface is an iterator that is a generalized description for numerical methods of solving differential
 * equations. It is consisting of:
 * @param limit: if the limit becomes true then the iteration would stop.
 * @param method: is the numerical algorithm to get the next point of value from the current one.
 * @param differential: given the current point of value differentials would return the necessary values to calculate the
 * next point of value for the method.
 *
 * @property hasNext: checks if the limit has been arrived or not.
 * @property next: returns the next computed point of value.
 * @property preSolve: runs before the solving process starts.
 * @property postSolve: runs once after the completion of solver.
 * @property preStep: runs every time before a given step.
 * @property postStep: runs everytime after a given step.
 * @property evaluate: runs the whole solver and catches the next values until we reach the limit.
 */
interface Solver<T> : Iterator<T> {
    val limit: Limit<T>
    val stepSize: Double
    val method: Method<T>
    val differential: (T) -> T
    var currentStepPoint: T

    /**
     * Checks if the limit has been arrived or not.
     */
    override fun hasNext(): Boolean = !currentStepPoint.limit()

    /**
     * @return the next computed point of value.
     */
    override fun next(): T {
        preStep()
        currentStepPoint = currentStepPoint.method(stepSize, differential)
        postStep()
        return currentStepPoint
    }

    /**
     * Runs before the solving process starts.
     */
    fun preSolve(): Unit


    /**
     * Runs after the solver is has ended
     */
    fun postSolve(): Unit

    /**
     * Runs everytime before a given step.
     */
    fun preStep(): Unit

    /**
     * Runs everytime after a given step.
     */
    fun postStep(): Unit

    // Runs the whole solver and catches the next values until we reach the limit.
    open fun evaluate(): Sequence<T> {
        preSolve()
        val evaluations: Sequence<T> = sequence {
            yield(next())
        }
        postSolve()
        return evaluations
    }

}
