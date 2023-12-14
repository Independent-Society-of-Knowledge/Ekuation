package base.definitions


/**
 * Point is the representation of any type of values that one might need for solving a set of differential equations.
 * For a better computation runtime the values are all stored in the private val data.
 */
data class Point(
    private val data: DoubleArray
) {

    /**
     * Getting a value from the array based on its index.
     */
    operator fun get(index: Int) = data[index]

    /**
     * Setting new values for a given index.
     */
    operator fun set(index: Int, value: Double) {
        data[index] = value
    }

    // Dimension of the Point vector.
    val dimension
        get() = data.size

    /**
     * Checks the dimensions of two Points, which is necessary for some operators.
     */
    private inline fun throwIfMismatch(other: Point) {
        assert(other.dimension == this.dimension) {
            throw IllegalArgumentException("The dimension of two points must be equal. left: ${this.dimension} right: ${other.dimension}")
        }
    }

    /**
     * Adding two Points with the same dimension together.
     */
    operator fun plus(other: Point): Point {
        throwIfMismatch(other)
        return Point(
            DoubleArray(dimension) {
                this.data[it] + other.data[it]
            }
        )
    }

    /**
     * Subtracting two Points with the same dimension from eachother.
     */
    operator fun minus(other: Point): Point {
        throwIfMismatch(other)
        return Point(
            DoubleArray(dimension) {
                this.data[it] - other.data[it]
            }
        )
    }

    /**
     * Similar to vector * scalar in mathematics, we define the multiplication of a number to our data as follows:
     */
    operator fun times(n: Number): Point {
        val nAsDouble = n.toDouble()
        return Point(DoubleArray(dimension) { this.data[it] * nAsDouble })
    }

    /**
     * Division is just multiplication with the inverse of the number.
     */
    operator fun div(n: Number): Point = this.times(1.0 / n.toDouble())

    /**
     * Dot Product.
     */
    infix fun dot(other: Point): Double {
        throwIfMismatch(other)
        var acc = 0.0
        for (i in 0..<dimension) {
            acc += data[i] * other.data[i]
        }
        return acc
    }

    /**
     * Checks if two Points are in-fact equal to eachother.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        return data.contentEquals(other.data)
    }

    /**
     * Generates the hasCode for the point.
     */
    override fun hashCode(): Int {
        return data.contentHashCode()
    }

    /**
     * Transforming to String.
     */
    override fun toString(): String = data.contentToString()

    /**
     * Copying a Point
     */
    fun copy(): Point = Point(data.clone())


}


/**
 * Utils:
 * 1. Number.times... an operator declarartion for multiplying a number with a Point.
 * 2. fun pointOf(vararg elements: Double) -> Point(elements) creates a point with given values.
 */

operator fun Number.times(other: Point) = other.times(this)
fun pointOf(vararg elements: Double) = Point(elements)

