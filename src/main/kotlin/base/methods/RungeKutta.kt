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
