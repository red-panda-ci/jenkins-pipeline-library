// src/org/foo/Point.groovy
package org.foo;

// point in 3D space
class Point implements Serializable {
  float x,y,z;
  def testEcho () {
    echo "test!"
    sh 'echo Hello, world'
  }
}
