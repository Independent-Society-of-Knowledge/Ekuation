/**
 * Copyright (c) 2023, Independent Society of Knowledge
 * All rights reserved.
 *
 * Author: Amir H. Ebrahimnezhad
 * Contact: projects@thisismeamir.com
 *
 * This code is the intellectual property of the Independent Society of Knowledge,
 * a community of scientists and researchers in different areas. Any unauthorized use,
 * reproduction, or distribution of this code or any portion thereof is strictly
 * prohibited. Permission to use, copy, modify, or distribute this software for any
 * purpose is hereby granted, provided that the above copyright notice and this
 * permission notice appear in all copies and that the name of the Independent Society
 * of Knowledge not be used in advertising or publicity pertaining to distribution
 * of the software without specific, written prior permission.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
